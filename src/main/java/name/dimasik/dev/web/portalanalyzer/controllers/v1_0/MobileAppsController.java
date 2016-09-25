package name.dimasik.dev.web.portalanalyzer.controllers.v1_0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/v1.0/portalMobileApps")
public class MobileAppsController {
	
	private static final Logger logger = LoggerFactory.getLogger(MobileAppsController.class);

	@GetMapping("/apps")
	public void getRegisteredApps() {
		logger.info("Request to get registered applications.");
	}
	
	@PutMapping("/apps")
	public void registerApp() {
		logger.info("Request to register new application.");
	}
	
	@DeleteMapping("/apps/{id}")
	public void deleteApp(@PathVariable String id) {
		logger.info("Request to remove application with id: " + id);
		//TODO
	}
	
	@PutMapping("/run")
	public void runCheck() {
		logger.info("Request to run light check.");
		//TODO
	}
	
	@PutMapping("/runDeep")
	public void runDeepCheck() {
		logger.info("Request to run deep check.");
		//TODO
	}
	
	@PutMapping("/stop")
	public void stopCheck() {
		logger.info("Request to stop current check.");
		//TODO
	}
	
	@GetMapping("/status")
	public void getStatus() {
		logger.info("Request to get status.");
	}
	
	@GetMapping("/schedulerRules")
	public void getSchedulerRules() {
		logger.info("Request to get scheduler rules.");
	}
	
	@PutMapping("/schedulerRules")
	public void addSchedulerRule() {
		logger.info("Request to add new scheduler rule.");
	}
	
	@DeleteMapping("/schedulerRules/{id}")
	public void deleteSchedulerRule(@PathVariable String id) {
		logger.info("Request to delete scheduler rule with id: " + id);
		//TODO
	}
	
	@GetMapping("/report/{days}")
	public void getReport(@PathVariable String days) {
		logger.info("Request to get report. Days count: " + days);
	}
}
