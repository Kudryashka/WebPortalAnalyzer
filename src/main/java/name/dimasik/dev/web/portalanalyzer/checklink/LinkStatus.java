package name.dimasik.dev.web.portalanalyzer.checklink;

/**
 * Represents a status of a link.
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 */
public enum LinkStatus {
	/**
	 * Resource represented by the link URL successfully reached.
	 */
	OK,
	/**
	 * Redirect required by the link URL.
	 */
	REDIRECT,
	/**
	 * Error code received by the link URL.
	 */
	ERROR,
	/**
	 * Resource represented by the link URL unreachable.
	 */
	UNREACHABLE
}
