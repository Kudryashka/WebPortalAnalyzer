package name.dimasik.dev.web.portalanalyzer.prefs;

/**
 * Indicates about preference with an illegal type.
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 */
public class IllegalPreferenceTypeException extends IllegalArgumentException {

	private static final long serialVersionUID = -4943637496351709900L;

	/**
	 * Created new exception with default message.
	 * @see IllegalPreferenceTypeException
	 */
	public IllegalPreferenceTypeException() {
		super("Preference with the current type not allowed.");
	}
}
