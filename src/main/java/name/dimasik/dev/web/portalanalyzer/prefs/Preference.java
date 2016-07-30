package name.dimasik.dev.web.portalanalyzer.prefs;

/**
 * Represents a list of possible application preferences and their value types.
 * @see Type
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 */
public enum Preference {
	
	/**
	 * TODO
	 */
	WEB_PORTAL_CHECK_META_KEY(Type.STRING),
	/**
	 * TODO
	 */
	WEB_PORTAL_CHECK_META_VALUE(Type.STRING),
	/**
	 * TODO
	 */
	PORTAL_DOMAIN_NAME(Type.STRING),
	/**
	 * TODO
	 */
	PORTAL_PORT_NUMBER(Type.INTEGER),
	/**
	 * TODO
	 */
	PORTAL_PROTOCOL_HTTP(Type.BOOLEAN),
	/**
	 * TODO
	 */
	PORTAL_PROTOCOL_HTTPS(Type.BOOLEAN),
	/**
	 * TODO
	 */
	CHECK_LINK_START_LOCATIONS(Type.STRING_ARRAY),
	/**
	 * TODO
	 */
	CHECK_LINK_ON_LOAD_PAGE_BACKGOUND_JOB_TIMEOUT(Type.LONG),
	/**
	 * TODO
	 */
	CHECK_LINK_ACCEPTABLE_REDIRECT_DEPTH(Type.INTEGER);
	
	private Type type;
	
	private Preference(Type type) {
		this.type = type;
	}
	
	/**
	 * Returns a type of the preference
	 * @see Type 
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Possible types of {@link Preference}
	 * @see Preference
	 * @author Dmytro Kudria
	 * @author <a href="http://dimasik.name">http://dimasik.name</a>
	 */
	public enum Type {
		STRING,
		STRING_ARRAY,
		BOOLEAN,
		INTEGER,
		LONG
	}
}
