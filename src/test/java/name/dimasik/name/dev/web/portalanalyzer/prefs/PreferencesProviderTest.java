package name.dimasik.name.dev.web.portalanalyzer.prefs;

import org.junit.BeforeClass;
import org.junit.Test;

import name.dimasik.dev.web.portalanalyzer.prefs.IllegalPreferenceTypeException;
import name.dimasik.dev.web.portalanalyzer.prefs.Preference;
import name.dimasik.dev.web.portalanalyzer.prefs.Preference.Type;
import name.dimasik.dev.web.portalanalyzer.prefs.PreferencesProvider;

/**
 * Tests for {@link PreferencesProvider}
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 */
public class PreferencesProviderTest {
	
	private static PreferencesProvider testProvider;
	
	@BeforeClass
	public static void init() {
		testProvider = new PreferencesProvider();
	}

	/**
	 * Tests for success getting of a preference with {@link Type#STRING}
	 */
	@Test
	public void testGetStringPreference() {
		testProvider.getStringPreference(Preference.PORTAL_DOMAIN_NAME);
	}
	
	/**
	 * Tests for fail getting of a preference with wrong type as a preference with {@link Type#STRING}
	 */
	@Test(expected = IllegalPreferenceTypeException.class)
	public void testGetStringPreferenceError() {
		testProvider.getStringPreference(Preference.CHECK_LINK_ON_LOAD_PAGE_BACKGOUND_JOB_TIMEOUT);
	}
	
	/**
	 * Tests for success getting of a preference with {@link Type#STRING_ARRAY}
	 */
	@Test
	public void testGetStringArrayPreference() {
		testProvider.getStringArrayPreference(Preference.CHECK_LINK_START_LOCATIONS);
	}
	
	/**
	 * Tests for fail getting of a preference with wrong type as a preference with {@link Type#STRING_ARRAY}
	 */
	@Test(expected = IllegalPreferenceTypeException.class)
	public void testGetStringArrayPreferenceError() {
		testProvider.getStringArrayPreference(Preference.PORTAL_DOMAIN_NAME);
	}
	
	/**
	 * Tests for success getting of a preference with {@link Type#BOOLEAN}
	 */
	@Test
	public void testGetBooleanPreference() {
		testProvider.getBooleanPreference(Preference.PORTAL_PROTOCOL_HTTP);
	}
	
	/**
	 * Tests for fail getting of a preference with wrong type as a preference with {@link Type#BOOLEAN}
	 */
	@Test(expected = IllegalPreferenceTypeException.class)
	public void testGetBooleanPreferenceError() {
		testProvider.getBooleanPreference(Preference.PORTAL_DOMAIN_NAME);
	}
	
	/**
	 * Tests for success getting of a preference with {@link Type#INTEGER}
	 */
	@Test
	public void testGetIntegerPreference() {
		testProvider.getIntegerPreference(Preference.PORTAL_PORT_NUMBER);
	}
	
	/**
	 * Tests for fail getting of a preference with wrong type as a preference with {@link Type#INTEGER}
	 */
	@Test(expected = IllegalPreferenceTypeException.class)
	public void testGetIntegerPreferenceError() {
		testProvider.getIntegerPreference(Preference.PORTAL_DOMAIN_NAME);
	}
	
	/**
	 * Tests for success getting of a preference with {@link Type#LONG}
	 */
	@Test
	public void testGetLongPreference() {
		testProvider.getLongPreference(Preference.CHECK_LINK_ON_LOAD_PAGE_BACKGOUND_JOB_TIMEOUT);
	}
	
	/**
	 * Tests for fail getting of a preference with wrong type as a preference with {@link Type#LONG}
	 */
	@Test(expected = IllegalPreferenceTypeException.class)
	public void testGetLongPreferenceError() {
		testProvider.getLongPreference(Preference.PORTAL_DOMAIN_NAME);
	}
}
