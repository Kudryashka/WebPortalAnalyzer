package name.dimasik.dev.web.portalanalyzer;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@GetMapping("/testPageWithLinks")
	public String getTestHome() {
		logger.info("getTestPageWithLinks()");
		
		return "home";
	}
	
	@GetMapping("/testUserInfo")
	public String getTestUserInfo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		logger.info("getTestUsetInfo()");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/processRawUserRequest");
		dispatcher.include(request, response);
		
		return "home";
	}
}
