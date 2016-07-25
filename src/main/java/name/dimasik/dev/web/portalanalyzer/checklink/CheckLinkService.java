package name.dimasik.dev.web.portalanalyzer.checklink;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CheckLinkService {

	static {
		// Assume SecurityException should not be thrown 
		HttpURLConnection.setFollowRedirects(false);
	}
	
	/**
	 * Get status of the link described by the URL
	 * @param url The URL
	 * @return Link access status as {@link AccessStatus}
	 * @throws MalformedURLException
	 */
	public AccessStatus checkLinkAccessStatus(final String url) throws MalformedURLException {
		URL urlObj = new URL(url);
		try {
			HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
			initConnectionParams(conn, null);
			
			int responseCode = conn.getResponseCode();
			//Check for redirect to same URL
			if (responseCode >= 300 && responseCode < 400) { 
				String redirectUrl = conn.getHeaderField("Location");
				if (isUrlsToSameLocation(url, redirectUrl)) {
					String cookies = conn.getHeaderField("Set-Cookie");
					
					URL redirectUrlObj = new URL(redirectUrl);
					HttpURLConnection redirectConn = (HttpURLConnection) redirectUrlObj.openConnection();
					initConnectionParams(redirectConn, cookies);
					responseCode = redirectConn.getResponseCode();
				}
			}
			
			if (responseCode < 300) { //2xx - success
				return AccessStatus.ACCESSIBLE;
			} else if (responseCode < 400) { //3xx - redirect
				return AccessStatus.REDIRECT;
			} else if (responseCode < 500) { //4xx - client error
				return AccessStatus.ERROR;
			} else { //5xx - server error
				return AccessStatus.ERROR;
			}
		} catch (IOException e) {
			return AccessStatus.INACCESSIBLE;
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
	public boolean isUrlsToSameLocation(String url1, String url2) {
		String[] urlSplit1 = prepareUrlToCompare(url1);
		String[] urlSplit2 = prepareUrlToCompare(url2);
		
		return urlSplit1[0].equals(urlSplit2[0]) && urlSplit1[1].equals(urlSplit2[1]);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<LinkInfo> getAllLinksOnPage(String url) {
		
		return null;
	}
	
	private void initConnectionParams(HttpURLConnection conn, String cookies) throws ProtocolException {
		conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0");
		if (cookies != null) {
			conn.setRequestProperty("Cookie", cookies);
		}
	}
	
	private String[] prepareUrlToCompare(String url) {
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
