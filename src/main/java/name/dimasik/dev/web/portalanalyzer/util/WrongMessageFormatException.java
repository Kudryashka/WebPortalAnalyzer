package name.dimasik.dev.web.portalanalyzer.util;

/**
 * Exception for illegal format of request message.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class WrongMessageFormatException extends Exception {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct new exception object with detail message.
	 */
	public WrongMessageFormatException(String message) {
		super(message);
	}
}
