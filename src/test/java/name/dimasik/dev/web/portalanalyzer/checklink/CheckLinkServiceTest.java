package name.dimasik.dev.web.portalanalyzer.checklink;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;

public class CheckLinkServiceTest {
	
	public static final String INVALID_LINK = "foo://test";
	public static final String ACCESSIBLE_LINK = "http://localhost:8080/WebPortalAnalyzer/";
	public static final String ERROR_LINK = "http://localhost:8080/WebPortalAnalyzer/unbelivableerrorpage";
	public static final String INACCESSIBLE_LINK = "http://localhost:9173/";
	
	public static final String TEST_PAGE_WITH_LINKS = "http://localhost:8080/WebPortalAnalyzer/testPageWithLinks";

	private static CheckLinkService mService;
	
	@BeforeClass
	public static void init() {
		mService = new CheckLinkService();
	}
	
	/**
	 * Test link status check for illegal argument.
	 */
	@Test(expected = MalformedURLException.class)
	public void testInvalidLinkFormat() throws MalformedURLException {
		mService.checkLinkAccessStatus(INVALID_LINK);
	}
	
	/**
	 * Test link status check for a link with status 'ACCESSIBLE'.
	 * Assume a resource available on {@link CheckLinkServiceTest#ACCESSIBLE_LINK}
	 */
	@Test
	public void testAccessibleLink() throws MalformedURLException {
		AccessStatus status = mService.checkLinkAccessStatus(ACCESSIBLE_LINK);
		assertEquals(AccessStatus.ACCESSIBLE, status);
	}
	
	/**
	 * Test link status check for a link with status 'ERROR'.
	 * Assume a resource unavailable on {@link CheckLinkServiceTest#ERROR_LINK}
	 */
	@Test
	public void testErrorLink() throws MalformedURLException {
		AccessStatus status = mService.checkLinkAccessStatus(ERROR_LINK);
		assertEquals(AccessStatus.ERROR, status);
	}
	
	/**
	 * Test link status check for a link with status 'INACCESSIBLE'.
	 * Assume there are no services accessible by address {@link CheckLinkServiceTest#INACCESSIBLE_LINK}
	 */
	@Test
	public void testInaccessibleLink() throws MalformedURLException {
		AccessStatus status = mService.checkLinkAccessStatus(INACCESSIBLE_LINK);
		assertEquals(AccessStatus.INACCESSIBLE, status);
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
	
	@Test
	public void testLinksSearchOnPage() {
		List<LinkInfo> links = mService.getAllLinksOnPage(TEST_PAGE_WITH_LINKS);
		List<LinkInfo> anchors = links.stream()
				.filter(link -> link.type == LinkType.ANCHOR)
				.collect(Collectors.toList()); 
		assertTrue(anchors.size() == 5);
	}
	
	@Test
	public void testGoogle() throws MalformedURLException {
		AccessStatus status = mService.checkLinkAccessStatus("https://google.com.ua");
		assertEquals(AccessStatus.ACCESSIBLE, status);
	}
	
	@Test
	public void testUkrNet() throws MalformedURLException {
		AccessStatus status = mService.checkLinkAccessStatus("https://ukr.net/");
		assertEquals(AccessStatus.ACCESSIBLE, status);
	}
}
