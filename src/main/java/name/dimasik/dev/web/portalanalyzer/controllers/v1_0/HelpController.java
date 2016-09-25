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

@RestController
@RequestMapping("/api/v1.0/portalHelp")
public class HelpController {
	
	private static final Logger logger = LoggerFactory.getLogger(HelpController.class);

	@GetMapping("/onlineCriteria")
	public void getOnlineCriteria() {
		logger.info("Request to get online criteria.");
	}
	
	@GetMapping("/offlineCriteria")
	public void getOfflineCriteria() {
		logger.info("Request to get offline criteria.");
	}
	
	@PutMapping("/onlineCriteria")
	public void setOnlineCriteria() {
		logger.info("Request to update online criteria.");
	}
	
	@PutMapping("/offlineCriteria")
	public void setOfflineCriteria() {
		logger.info("Request to update offline criteria.");
	}
	
	@DeleteMapping("/onlineCriteria")
	public void deleteOnlineCriteria() {
		logger.info("Request to delete online criteria.");
		//TODO
	}
	
	@DeleteMapping("/offlineCriteria")
	public void deleteOfflineCriteria() {
		logger.info("Request to delete offline criteria.");
		//TODO
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
