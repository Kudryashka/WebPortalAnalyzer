package name.dimasik.dev.web.portalanalyzer.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private static Logger logger = LoggerFactory.getLogger(RestAuthenticationSuccessHandler.class);

	private RequestCache requestCache = new HttpSessionRequestCache();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("Authentication success!");
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest == null) {
			clearAuthenticationAttributes(request);
		} else {
			String targetUrl = getTargetUrlParameter();
			if (isAlwaysUseDefaultTargetUrl() || (targetUrl != null 
					&& StringUtils.hasText(request.getParameter(targetUrl)))) {
			            requestCache.removeRequest(request, response);
			            clearAuthenticationAttributes(request);
			            return;
			}
			 
			clearAuthenticationAttributes(request);
		}
	}
}
