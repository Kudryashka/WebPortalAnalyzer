package name.dimasik.dev.web.portalanalyzer.controllers.v1_0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@RestController
@RequestMapping("/v1.0/portalUsersInfo")
@CrossOrigin
public class UsersInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(UsersInfoController.class);

	@PutMapping
	public void addUserInfo() {
		logger.info("Request to register new user info.");
	}

	@GetMapping("/report/{days}")
	public void getReport(@PathVariable String days) {
		logger.info("Request to get report. Days count: " + days);
	}
}
