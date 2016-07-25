package name.dimasik.dev.web.portalanalyzer;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.annotation.JsonView;

import name.dimasik.dev.web.portalanalyzer.userinfo.UserInfoService;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private UserInfoService userInfoService;
	
	@Autowired
	public void setUserInfoService(UserInfoService userInfoService) {
		logger.info("User info service initialized.");
		this.userInfoService = userInfoService;
	}
	
	/*
	 * HTTP requests mappings
	 */
	
	@GetMapping("/processRawUserRequest")
	@ResponseStatus(HttpStatus.OK)
	public void processRawUserRequest(HttpServletRequest request) {
		logger.info("processRawUserRequest()");
		userInfoService.processRawUserRequest(request);
	}
	
	/*
	 * REST requests mappings 
	 */
	
	@GetMapping("/")
	@ResponseBody
	@JsonView(Info.InfoView.class)
	public Info home(Locale locale, Model model) {
		logger.info("Home");
		return new Info("home");
	}
	
	public static class Info {

		public interface InfoView {};
		
		private final String info;
		private final String[] details;
		
		public Info(String info) {
			this.info = info;
			this.details = new String[]{"test details", "dfjls"};
		}
		
		@JsonView(InfoView.class)
		public String getInfo() {
			return info;
		}
		
		@JsonView(InfoView.class)
		public String[] getDetails() {
			return details;
		}
	}
}
