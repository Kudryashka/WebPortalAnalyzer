package name.dimasik.dev.web.portalanalyzer.checklink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import name.dimasik.dev.web.portalanalyzer.prefs.Preference;
import name.dimasik.dev.web.portalanalyzer.prefs.PreferencesProvider;

/**
 * Tests for {@link CheckLinkService} 
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 */
public class CheckLinkServiceTest {
	
	public static final String INVALID_LINK = "foo://test";
	public static final String ACCESSIBLE_LINK = "http://localhost:8080/WebPortalAnalyzer/api/testHome";
	public static final String ERROR_LINK = "http://localhost:8080/WebPortalAnalyzer/api/unbelivableerrorpage";
	public static final String UNREACHABLE_LINK = "http://localhost:9173/";
	
	public static final String TEST_HOME_PAGE = "http://localhost:8080/WebPortalAnalyzer/api/testHome";
	public static final String TEST_PAGE_WITH_LINKS = "http://localhost:8080/WebPortalAnalyzer/api/testPageWithLinks";

	private static CheckLinkService mService;
	
	@BeforeClass
	public static void init() {
		mService = new CheckLinkService();
		mService.setPreferencesProvider(new PreferencesProvider() {
			@Override
			public String getStringPreference(Preference pref) {
				switch(pref) {
					case PORTAL_DOMAIN_NAME:
						return "localhost";
					default:
						return super.getStringPreference(pref);
					
				}
			}
			@Override
			public String[] getStringArrayPreference(Preference pref) {
				switch(pref) {
				case CHECK_LINK_START_LOCATIONS:
					return new String[] {"WebPortalAnalyzer/api/testPageWithLinks"};
				default:
					return super.getStringArrayPreference(pref);
				
			}
			}
			@Override
			public boolean getBooleanPreference(Preference pref) {
				switch(pref) {
					case PORTAL_PROTOCOL_HTTP:
						return true;
					default:
						return false;
				}
			}
			@Override
			public int getIntegerPreference(Preference pref) {
				switch (pref) {
					case PORTAL_PORT_NUMBER:
						return 8080;
					default:
						return super.getIntegerPreference(pref);
				}
			}
			
		});
		mService.onInitService();
	}
	
	@AfterClass
	public static void destroy() {
		mService.onDestroyService();
	}
	
	/**
	 * Test link status check for illegal argument.
	 */
	@Test(expected = MalformedURLException.class)
	public void testInvalidLinkFormat() throws MalformedURLException {
		mService.checkLinkAccessStatus(INVALID_LINK);
	}
	
	/**
	 * Test link status check for a link with status {@link LinkStatus#OK}.
	 * Assume a resource available on {@link CheckLinkServiceTest#ACCESSIBLE_LINK}
	 */
	@Test
	public void testAccessibleLink() throws MalformedURLException {
		LinkStatus status = mService.checkLinkAccessStatus(ACCESSIBLE_LINK).linkStatus;
		assertEquals(LinkStatus.OK, status);
	}
	
	/**
	 * Test link status check for a link with status {@link LinkStatus#ERROR}.
	 * Assume a resource unavailable on {@link CheckLinkServiceTest#ERROR_LINK}
	 */
	@Test
	public void testErrorLink() throws MalformedURLException {
		LinkStatus status = mService.checkLinkAccessStatus(ERROR_LINK).linkStatus;
		assertEquals(LinkStatus.ERROR, status);
	}
	
	/**
	 * Test link status check for a link with status {@link LinkStatus#UNREACHABLE}.
	 * Assume there are no services accessible by address {@link CheckLinkServiceTest#UNREACHABLE_LINK}
	 */
	@Test
	public void testInaccessibleLink() throws MalformedURLException {
		LinkStatus status = mService.checkLinkAccessStatus(UNREACHABLE_LINK).linkStatus;
		assertEquals(LinkStatus.UNREACHABLE, status);
	}
	
	/**
	 * Test utility method {@link CheckLinkService#isUrlsToSameLocation(String, String)}
	 */
	@Test
	public void testUrlsComparation() {
		assertFalse(mService.isUrlsToSameLocation("http://localhost", "https://localhost"));
		assertTrue(mService.isUrlsToSameLocation("http://localhost", "http://localhost"));
		assertTrue(mService.isUrlsToSameLocation("http://localhost", "http://localhost/"));
		assertFalse(mService.isUrlsToSameLocation("http://localhost", "http://localhost:8080"));
		assertTrue(mService.isUrlsToSameLocation("http://localhost", "http://www.localhost/"));
		assertTrue(mService.isUrlsToSameLocation("http://localhost:8080////", "http://localhost:8080/"));
		assertTrue(mService.isUrlsToSameLocation("http://localhost:8080////test", "http://localhost:8080/test"));
	}
	
	/**
	 * Test search of links on a test page.
	 * Assume server is running and the test page accessible by the {@link CheckLinkServiceTest#TEST_PAGE_WITH_LINKS} 
	 */
	@Test
	public void testLinksSearchOnPage() {
		List<LinkInfo> links = mService.getAllLinksOnPage(TEST_PAGE_WITH_LINKS);
		List<LinkInfo> anchors = links.stream()
				.filter(link -> link.type == LinkType.ANCHOR)
				.collect(Collectors.toList()); 
		assertTrue(anchors.size() == 5);
	}
	
	/**
	 * Test {@link CheckLinkService#buildURL(String, String, String)}.
	 */
	@Test
	public void testBuildUrl() {
		String httpUrl = CheckLinkService.buildURL("http", "test", 80, "testPath");
		String httpsUrl = CheckLinkService.buildURL("https", "test", 443, "testPath");
		String https80Url = CheckLinkService.buildURL("https", "test", 80, "testPath");
		String http8080Url = CheckLinkService.buildURL("http", "test", 8080, "testPath");
		
		assertEquals(httpUrl, "http://test/testPath");
		assertEquals(httpsUrl, "https://test/testPath");
		assertEquals(https80Url, "https://test:80/testPath");
		assertEquals(http8080Url, "http://test:8080/testPath");
	}
	
	/**
	 * Test {@link CheckLinkService#transformRelativeLinksToAbsolute(List)}
	 */
	@Test
	public void testTransformRelativeLinksToAbsolute() {
		List<LinkInfo> links = new ArrayList<>();
		links.add(new LinkInfo(LinkType.ANCHOR, "page1", "http://localhost"));
		links.add(new LinkInfo(LinkType.ANCHOR, "http://localhost/page2", "http://localhost"));
		links.add(new LinkInfo(LinkType.ANCHOR, "http://another", "http://localhost"));
		links.add(new LinkInfo(LinkType.ANCHOR, "relative", "http://localhost/path"));
		
		List<LinkInfo> transformed = CheckLinkService.transformRelativeLinksToAbsolute(links);
		assertEquals(transformed.size(), 4);
		assertTrue(transformed.stream().anyMatch(info -> info.targetUrl.equals("http://localhost/page1")));
		assertTrue(transformed.stream().anyMatch(info -> info.targetUrl.equals("http://localhost/page2")));
		assertTrue(transformed.stream().anyMatch(info -> info.targetUrl.equals("http://another")));
		assertTrue(transformed.stream().anyMatch(info -> info.targetUrl.equals("http://localhost/relative")));
	}
	
	/**
	 * Test {@link CheckLinkService#filterAbsoluteLinksByProtocol(List)}
	 */
	@Test
	public void testFilterAbsoluteLinksByProtocol() {
		List<LinkInfo> links = new ArrayList<>();
		links.add(new LinkInfo(LinkType.ANCHOR, "http://localhost", null));
		links.add(new LinkInfo(LinkType.ANCHOR, "http://localhost2", null));
		links.add(new LinkInfo(LinkType.ANCHOR, "https://localhost3", null));
		links.add(new LinkInfo(LinkType.ANCHOR, "ftp://localhost", null));
		links.add(new LinkInfo(LinkType.ANCHOR, "ssh://localhost", null));
		links.add(new LinkInfo(LinkType.ANCHOR, "httpx://localhost", null));
		
		
		List<LinkInfo> filtered = CheckLinkService.filterAbsoluteLinksByProtocol(links);
		assertEquals(filtered.size(), 3);
		assertTrue(filtered.stream().anyMatch(info -> info.targetUrl.equals("http://localhost")));
		assertTrue(filtered.stream().anyMatch(info -> info.targetUrl.equals("http://localhost2")));
		assertTrue(filtered.stream().anyMatch(info -> info.targetUrl.equals("https://localhost3")));
	}
	
	/**
	 * Test links check on the portal.
	 * Assume server is running and testing pages accessible.
	 * List of used test pages:
	 * <ul>
	 * <li>{@link CheckLinkServiceTest#TEST_PAGE_WITH_LINKS}
	 * <li>{@link CheckLinkServiceTest#TEST_HOME_PAGE}
	 * </ul>
	 * One of the test pages should contain link with href="http://Oops"
	 * @see CheckLinkService#checkLinksOnPortal()
	 */
	@Test
	public void testCheckLinksOnPortal() {
		List<LinkInfo> links = mService.checkLinksOnPortal();
		
		System.out.println(links);
		
		assertTrue(links.stream().anyMatch(link -> link.targetUrl.equalsIgnoreCase("http://Oops") && link.linkStatus == LinkStatus.UNREACHABLE));
		assertTrue(links.stream().anyMatch(link -> link.targetUrl.equalsIgnoreCase("http://localhost:8080/WebPortalAnalyzer/api/testPageWithLinks") && link.linkStatus == LinkStatus.OK));
		assertTrue(links.stream().anyMatch(link -> link.targetUrl.equalsIgnoreCase("http://localhost:8080/WebPortalAnalyzer/api/testHome") && link.linkStatus == LinkStatus.OK));
	}
}
