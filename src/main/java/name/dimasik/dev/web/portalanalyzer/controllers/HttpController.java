package name.dimasik.dev.web.portalanalyzer.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import name.dimasik.dev.web.portalanalyzer.userinfo.UserInfoService;

/**
 * TODO delete this controller
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@Controller
@RequestMapping("/api/http/")
@Deprecated
public class HttpController {

	private static final Logger logger = LoggerFactory.getLogger(HttpController.class);
	
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
}
