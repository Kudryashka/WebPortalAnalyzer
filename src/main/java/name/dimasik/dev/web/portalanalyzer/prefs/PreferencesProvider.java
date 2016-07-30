package name.dimasik.dev.web.portalanalyzer.prefs;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import name.dimasik.dev.web.portalanalyzer.prefs.Preference.Type;

/**
 * Provider of application preferences.
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 */
@Service
public class PreferencesProvider {
	
	/**
	 * TODO temporary
	 */
	//====================================================================================
	private HashMap<Preference, String> stringPrefs = new HashMap<>();
	private HashMap<Preference, String[]> stringArrayPrefs = new HashMap<>();
	private HashMap<Preference, Boolean> boolPrefs = new HashMap<>();
	private HashMap<Preference, Integer> intPrefs = new HashMap<>();
	private HashMap<Preference, Long> longPrefs = new HashMap<>();
	//====================================================================================
	{//string
		stringPrefs.put(Preference.PORTAL_DOMAIN_NAME, "localhost");
		stringPrefs.put(Preference.WEB_PORTAL_CHECK_META_KEY, "web_portal_check");
		stringPrefs.put(Preference.WEB_PORTAL_CHECK_META_VALUE, "");
	}
	{//bool
		boolPrefs.put(Preference.PORTAL_PROTOCOL_HTTP, true);
		boolPrefs.put(Preference.PORTAL_PROTOCOL_HTTPS, false);
	}
	{//int
		intPrefs.put(Preference.PORTAL_PORT_NUMBER, 80);
		intPrefs.put(Preference.CHECK_LINK_ACCEPTABLE_REDIRECT_DEPTH, 3);
	}
	{//long
		longPrefs.put(Preference.CHECK_LINK_ON_LOAD_PAGE_BACKGOUND_JOB_TIMEOUT, 1000L);
	}
	//====================================================================================
	
	/**
	 * Returns a value for the {@link Preference} with {@link Type#STRING} or null if the preference not specified.
	 * @throws IllegalPreferenceTypeException
	 */
	public String getStringPreference(Preference pref) {
		checkPreferenceType(pref, Type.STRING);
		return stringPrefs.get(pref);
	}
	
	/**
	 * Returns a value for the {@link Preference} with {@link Type#STRING_ARRAY} or null if the preference not specified.
	 * @throws IllegalPreferenceTypeException
	 */
	public String[] getStringArrayPreference(Preference pref) {
		checkPreferenceType(pref, Type.STRING_ARRAY);
		return stringArrayPrefs.get(pref);
	}
	
	/**
	 * Returns a value for the {@link Preference} with {@link Type#BOOLEAN} or null if the preference not specified.
	 * @throws IllegalPreferenceTypeException
	 */
	public boolean getBooleanPreference(Preference pref) {
		checkPreferenceType(pref, Type.BOOLEAN);
		return boolPrefs.get(pref);
	}
	
	/**
	 * Returns a value for the {@link Preference} with {@link Type#INTEGER} or null if the preference not specified.
	 * @throws IllegalPreferenceTypeException
	 */
	public int getIntegerPreference(Preference pref) {
		checkPreferenceType(pref, Type.INTEGER);
		return intPrefs.get(pref);
	}
	
	/**
	 * Returns a value for the {@link Preference} with {@link Type#LONG} or null if the preference not specified.
	 * @throws IllegalPreferenceTypeException
	 */
	public long getLongPreference(Preference pref) {
		checkPreferenceType(pref, Type.LONG);
		return longPrefs.get(pref);
	}
	
	/**
	 * Check the preference type with the expected type.
	 * If types are different, throw {@link IllegalPreferenceTypeException}
	 * @throws IllegalPreferenceTypeException
	 */
	private void checkPreferenceType(Preference pref, Preference.Type type) {
		if (pref.getType() != type) {
			throw new IllegalPreferenceTypeException();
		}
	}
}
