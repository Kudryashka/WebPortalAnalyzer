package name.dimasik.dev.web.portalanalyzer.util;

/**
 * Utility class to parse common request values.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class Parser {

	/**
	 * Convert days represented as <code>String</code> to days represented as <code>int</code>
	 * @param days Days count as <code>String</code>
	 * @return Days count as <code>int</code>
	 * @throws DaysCountFormatException Throws when days count format is wrong.
	 */
	public static int parseDaysCount(String days) throws DaysCountFormatException {
		int result = 0;
		try {
			result = Integer.parseInt(days);
		} catch (NumberFormatException e) {
			throw new DaysCountFormatException("Wrong days count format. Days count should be represented as integer.");
		}
		
		if (result < 0) {
			throw new DaysCountFormatException("Days count can not be represented as negative number. Days count should be positive number.");
		}
		
		if (result == 0) {
			throw new DaysCountFormatException("Days count can not be zero. Days count should be bigger then zero.");
		}
		
		return result;
	}
	
	/**
	 * Exception for illegal format of days count.
	 *
	 * @author Dmytro Kudria
	 * @author <a href="http://dimasik.name">http://dimasik.name</a>
	 *
	 */
	public static class DaysCountFormatException extends Exception {
		
		private static final long serialVersionUID = 1L;

		/**
		 * Construct new exception object with detail message.
		 */
		public DaysCountFormatException(String message) {
			super(message);
		}
	}
}
