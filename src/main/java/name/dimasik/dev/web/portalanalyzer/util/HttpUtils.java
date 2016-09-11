package name.dimasik.dev.web.portalanalyzer.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility class to resolve standard operations with HTTP package.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class HttpUtils {

	/**
	 * Get IP address of the user.
	 * @param request User's request as {@link HttpServletRequest}
	 * @return IP address as {@link String}
	 */
	public static String getIP(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}
}
