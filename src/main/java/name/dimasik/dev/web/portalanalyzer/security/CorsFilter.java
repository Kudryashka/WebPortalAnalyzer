package name.dimasik.dev.web.portalanalyzer.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Add CORS headers and control CORS behavior. 
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class CorsFilter implements Filter {

	/**
	 * Do nothing
	 */
	@Override
	public void destroy() {
		
	}

	/**
	 * Add CORS headers
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String origin = request.getHeader("Origin");
		
		response.addHeader("Access-Control-Allow-Origin", origin == null ? "*" : origin);
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,HEAD,OPTIONS,PUT,PATCH,DELETE");
		response.setHeader("Access-Control-Allow-Headers", 
				"Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers");
		response.setHeader("Access-Control-Max-Age", "3600");
		
		if (request.getMethod().equals("OPTIONS")) {
			//Do not continue chain for OPTIONS request
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, res);
		}
	}

	/**
	 * Do nothing
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
