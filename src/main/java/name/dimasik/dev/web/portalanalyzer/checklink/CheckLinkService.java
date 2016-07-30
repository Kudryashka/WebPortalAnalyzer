package name.dimasik.dev.web.portalanalyzer.checklink;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import name.dimasik.dev.web.portalanalyzer.prefs.Preference;
import name.dimasik.dev.web.portalanalyzer.prefs.PreferencesProvider;

import static name.dimasik.dev.web.portalanalyzer.checklink.LinkType.*;

/**
 * Check accessibility of resources represented by the links on portal.
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 */
@Service
public class CheckLinkService {
	
	private static Logger logger = LoggerFactory.getLogger(CheckLinkService.class);

	static {
		// Assume SecurityException should not be thrown 
		HttpURLConnection.setFollowRedirects(false);
	}
	
	private WebClient webClient;
	private PreferencesProvider prefsProvider;

	/**
	 * <b>Don't call this manually!</b>
	 * Used to handle service initialization.
	 */
	@PostConstruct
	public final void onInitService() {
		webClient = new WebClient(BrowserVersion.FIREFOX_45);
		logger.info("Service initialized");
	}
	
	/**
	 * <b>Don't call this manually!</b>
	 * Used to handle service destroying.
	 */
	@PreDestroy
	public final void onDestroyService() {
		webClient.close();
		logger.info("Service destroyed");
	}
	
	/**
	 * Used to inject {@link PreferencesProvider}.
	 */
	@Autowired
	public void setPreferencesProvider(PreferencesProvider prefsProvider) {
		this.prefsProvider = prefsProvider;
	}
	
	/**
	 * Get status of the link described by the URL.
	 * @param url The URL.
	 * @return Link status as {@link LinkInfo}.
	 * @throws MalformedURLException
	 */
	public final LinkInfo checkLinkAccessStatus(final String url) throws MalformedURLException {
		URL urlObj = new URL(url);
		try {
			HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
			initConnectionParams(conn, null);
			
			int responseCode = conn.getResponseCode();
			//Check for redirect to same URL
			String redirectUrl = null;
			if (responseCode >= 300 && responseCode < 400) { 
				redirectUrl = conn.getHeaderField("Location");
				if (isUrlsToSameLocation(url, redirectUrl)) {
					String cookies = conn.getHeaderField("Set-Cookie");
					
					URL redirectUrlObj = new URL(redirectUrl);
					HttpURLConnection redirectConn = (HttpURLConnection) redirectUrlObj.openConnection();
					initConnectionParams(redirectConn, cookies);
					responseCode = redirectConn.getResponseCode();
					redirectUrl = redirectConn.getHeaderField("Location");
				}
			}
			
			if (responseCode < 300) { //2xx - success
				return new LinkInfo(UNKNOWN, url, null, LinkStatus.OK, responseCode);
			} else if (responseCode < 400) { //3xx - redirect
				return new LinkInfo(UNKNOWN, url, null, LinkStatus.REDIRECT, responseCode, redirectUrl);
			} else if (responseCode < 500) { //4xx - client error
				return new LinkInfo(UNKNOWN, url, null, LinkStatus.ERROR, responseCode);
			} else { //5xx - server error
				return new LinkInfo(UNKNOWN, url, null, LinkStatus.ERROR, responseCode);
			}
		} catch (IOException e) {
			return new LinkInfo(UNKNOWN, url, null, LinkStatus.UNREACHABLE, -1);
		}
	}
	
	/**
	 * Check are locations described by URLs the same.
	 * Assume next: 
	 * <ul>
	 * <li>if protocols are different, the locations are different
	 * <li>if ports are different, the locations are different
	 * <li>URL that starts with 'www.' same with URL that doesn't start with 'www.'
	 * <li>'/' character at the end of the URL is meaningless
	 * <li>Sequence of '/' characters is meaningless and same with single '/' character
	 * </ul>
	 * @param url1 The first URL
	 * @param url2 The second URL
	 * @return true if URLs locations are same. 
	 */
	public final boolean isUrlsToSameLocation(String url1, String url2) {
		String[] urlSplit1 = prepareUrlToCompare(url1);
		String[] urlSplit2 = prepareUrlToCompare(url2);
		
		return urlSplit1[0].equals(urlSplit2[0]) && urlSplit1[1].equals(urlSplit2[1]);
	}
	
	/**
	 * Retrieve all links located on the page by the URL.
	 * @param url URL of the page.
	 * @return List of {@link LinkInfo} or an empty list.
	 */
	public final List<LinkInfo> getAllLinksOnPage(String url) {
		logger.info("All links requested. Url: " + url);
		List<LinkInfo> result = new ArrayList<>();

		HtmlPage page = null;
		long onLoadPageTimeout = prefsProvider.getLongPreference(
				Preference.CHECK_LINK_ON_LOAD_PAGE_BACKGOUND_JOB_TIMEOUT);
		
		synchronized (webClient) {
			try {
				page = webClient.getPage(url);
				//wait for background jobs
				webClient.waitForBackgroundJavaScript(onLoadPageTimeout);
			} catch (FailingHttpStatusCodeException | IOException e) {
				logger.error("Error on page loading. Exception message: " + e.getMessage());
			}
			
			if (page != null) {
				logger.debug("Catch links with type : Anchor");;
				//catch anchors
				List<DomElement> aElements = page.getElementsByTagName("a");
				for (DomElement e : aElements) {
					result.add(new LinkInfo(ANCHOR, e.getAttribute("href"), url));
				}
				
				//TODO add additional link types to search
			}
		}
			
		return result;
	}
	
	/**
	 * Check links on the portal.
	 * @return List with {@link LinkInfo} elements for every link founded on the portal from start locations.
	 * @see Preference#PORTAL_DOMAIN_NAME
	 * @see Preference#PORTAL_PORT_NUMBER
	 * @see Preference#PORTAL_PROTOCOL_HTTP
	 * @see Preference#PORTAL_PROTOCOL_HTTPS
	 * @see Preference#CHECK_LINK_START_LOCATIONS
	 * @see Preference#CHECK_LINK_ACCEPTABLE_REDIRECT_DEPTH
	 * @see Preference#WEB_PORTAL_CHECK_META_KEY
	 * @see Preference#WEB_PORTAL_CHECK_META_VALUE
	 */
	public final List<LinkInfo> checkLinksOnPortal() {
		logger.info("Start checking links on the portal");
		
		String portalDN = prefsProvider.getStringPreference(Preference.PORTAL_DOMAIN_NAME);
		int portalPort = prefsProvider.getIntegerPreference(Preference.PORTAL_PORT_NUMBER);
		boolean testHttp = prefsProvider.getBooleanPreference(Preference.PORTAL_PROTOCOL_HTTP);
		boolean testHttps = prefsProvider.getBooleanPreference(Preference.PORTAL_PROTOCOL_HTTPS);
		String[] startLocations = prefsProvider.getStringArrayPreference(Preference.CHECK_LINK_START_LOCATIONS);
		if (startLocations == null || startLocations.length == 0) {
			startLocations = new String[] {""}; 
		}
		int redirectDepth = prefsProvider.getIntegerPreference(Preference.CHECK_LINK_ACCEPTABLE_REDIRECT_DEPTH);
		
		Queue<LinkInfo> linksForScan = new LinkedList<>();
		for (String location : startLocations) {
			if (location != null) {
				if (testHttp) linksForScan.add(
						new LinkInfo(UNKNOWN, buildURL("http", portalDN, portalPort, location), null));
				if (testHttps) linksForScan.add(
						new LinkInfo(UNKNOWN, buildURL("https", portalDN, portalPort, location), null));
			}
		}

		List<LinkInfo> checkedLinks = new ArrayList<>();
		LinkInfo link;
		while ((link = linksForScan.poll()) != null) {
			String linkTargetUrl = link.targetUrl;
			if (checkedLinks.stream().anyMatch(l -> isUrlsToSameLocation(l.targetUrl, linkTargetUrl))) {
				continue; //no need to check same link twice
			}
			logger.debug("Process link: " + link.targetUrl);
			
			LinkInfo resultInfo = getResultInfoForSearchedLink(link);
			checkedLinks.add(resultInfo);
			
			if (resultInfo.linkStatus == LinkStatus.OK) {
				String checkedLinkUrl = prepareUrlToCompare(link.targetUrl)[1];
				while (checkedLinkUrl.startsWith("/")) checkedLinkUrl.substring(1);
				
				String[] clDnSplit = checkedLinkUrl.split("/")[0].split(":");
				boolean isSamePortal = clDnSplit[0].equals(portalDN);
				if (isSamePortal && clDnSplit.length > 1) {
					isSamePortal = clDnSplit[1].equals(String.valueOf(portalPort));
				}
				
				if (isSamePortal) {
					List<LinkInfo> linksOnPage = getAllLinksOnPage(resultInfo.targetUrl);
					linksOnPage = filterLinksWithoutTargetLocation(linksOnPage);
					linksOnPage = transformRelativeLinksToAbsolute(linksOnPage);
					linksOnPage = filterAbsoluteLinksByProtocol(linksOnPage);
					linksForScan.addAll(linksOnPage);
				}
			} else if (resultInfo.linkStatus == LinkStatus.REDIRECT) {
				LinkInfo redirectInfo = resultInfo;
				for (int i = 0; i < redirectDepth -1; ++i) {
					LinkInfo targetInfo = getResultInfoForSearchedLink(
							new LinkInfo(redirectInfo.type, redirectInfo.redirectTarget, redirectInfo.pageUrl));
					if (checkedLinks.contains(targetInfo)) {
						break; //was scanned before
					}
					if (targetInfo.linkStatus != LinkStatus.REDIRECT) {
						linksForScan.add(targetInfo); //need to scan normally
						break;
					} else {
						checkedLinks.add(targetInfo);
						redirectInfo = targetInfo; //go to next redirect target
					}
				}
			}
		}
		
		return checkedLinks;
	}
	
	/**
	 * Support method to get {@link #checkLinkAccessStatus(String)} result.
	 */
	private LinkInfo getResultInfoForSearchedLink(LinkInfo link) {
		LinkInfo resultInfo = null;
		try {
			LinkInfo linkStatusInfo = checkLinkAccessStatus(link.targetUrl);
			resultInfo = new LinkInfo(link.type, link.targetUrl, link.pageUrl, 
					linkStatusInfo.linkStatus, linkStatusInfo.responseCode, linkStatusInfo.redirectTarget);
		} catch (MalformedURLException e) {
			logger.error("Exception thrown when check links on portal!");
			resultInfo = new LinkInfo(link.type, link.targetUrl, link.pageUrl, 
					LinkStatus.UNREACHABLE, -1);
		}
		return resultInfo;
	}
	
	/**
	 * Filter a list with {@link ListInfo} to left only infos with non empty target URL
	 * @param links The list to be filtered
	 * @return Filtered list
	 */
	public List<LinkInfo> filterLinksWithoutTargetLocation(List<LinkInfo> links) {
		return links.stream()
				.filter(link -> (link.targetUrl != null && !link.targetUrl.isEmpty()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Transform {@link LinkInfo#targetUrl} to absolute path.
	 * @param links List with {@link LinkInfo} to be transformed
	 * @return List with transformed elements.
	 */
	public List<LinkInfo> transformRelativeLinksToAbsolute(List<LinkInfo> links) {
		List<LinkInfo> result = new ArrayList<>();
		for (LinkInfo info : links) {
			String targetUrl = info.targetUrl;
			if (targetUrl.contains("://")) {
				result.add(info);
			} else {
				String[] pageUrlSplit = info.pageUrl.split("://");
				String pageUrl = pageUrlSplit[1];
				
				while (pageUrl.endsWith("/")) pageUrl = pageUrl.substring(0, pageUrl.length() - 1);
				int idx = pageUrl.lastIndexOf("/");
				if (idx != -1) {
					pageUrl = pageUrl.substring(0, idx);
				}
				
				StringBuilder targetBuilder = new StringBuilder()
						.append(pageUrlSplit[0]).append("://").append(pageUrl)
						.append("/").append(info.targetUrl);
				result.add(new LinkInfo(info.type, targetBuilder.toString(), info.pageUrl));
			}
		}
		return result;
	}
	
	/**
	 * Filter a list with {@link ListInfo} to left only infos with supported for search protocols
	 * @param links The list to be filtered
	 * @return Filtered list
	 */
	public List<LinkInfo> filterAbsoluteLinksByProtocol(List<LinkInfo> links) {
		//http and https allowed yet
		return links.stream()
				.filter(link -> (link.targetUrl.startsWith("http://") || link.targetUrl.startsWith("https://")))
				.collect(Collectors.toList());
	}
	
	/**
	 * Builds URL via a protocol, a domain name, a port number and a path.
	 * @param protocol The protocol
	 * @param DN The domain name
	 * @param port The port number
	 * @param path The path
	 * @return Builded URL
	 */
	public final String buildURL(String protocol, String DN, int port, String path) {
		StringBuilder urlBuilder =  new StringBuilder(protocol).append("://").append(DN);

		boolean isDefaultPort = false;
		if (protocol.equals("http")) isDefaultPort = port == 80;
		if (protocol.equals("https")) isDefaultPort = port == 443;
		if (!isDefaultPort) {
			urlBuilder.append(":").append(port);
		}
		
		urlBuilder.append("/").append(path);
		return urlBuilder.toString();
	}
	
	/**
	 * Initialize connection parameters to emulate real user connection.
	 */
	private final void initConnectionParams(HttpURLConnection conn, String cookies) throws ProtocolException {
		conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0");
		//initialize special meta
		String requestSpecialMetaKey = prefsProvider.getStringPreference(Preference.WEB_PORTAL_CHECK_META_KEY);
		if (requestSpecialMetaKey != null && !requestSpecialMetaKey.isEmpty()) {
			String requestSpecialMetaValue = prefsProvider.getStringPreference(Preference.WEB_PORTAL_CHECK_META_VALUE);
			conn.setRequestProperty(requestSpecialMetaKey, requestSpecialMetaValue == null ? "" : requestSpecialMetaValue);
		}
		//initialize cookies
		if (cookies != null) {
			conn.setRequestProperty("Cookie", cookies);
		}
	}
	
	/**
	 * Simplify URL to standard format.
	 * @return A split of URL that represents protocol and URL tail separately.
	 */
	private static String[] prepareUrlToCompare(String url) {
		String[] urlSplit = url.trim().split("://"); //[0] - protocol, [1] - left part
		while (urlSplit[1].contains("//")) {
			urlSplit[1] = urlSplit[1].replace("//", "/");
		}
		while (urlSplit[1].endsWith("/")) {
			urlSplit[1] = urlSplit[1].substring(0, urlSplit[1].length() - 1);
		}
		if (urlSplit[1].startsWith("www.")) {
			urlSplit[1] = urlSplit[1].substring(4);
		}
		return urlSplit;
	}
}
