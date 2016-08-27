package name.dimasik.dev.web.portalanalyzer.controllers.v1_0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/v1.0/portalSettings")
@CrossOrigin
public class SettingsController {
	
	//TODO add setting -> a register of emptySearch queries 
	
	private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);

	@GetMapping
	public void getAllSettings() {
		logger.info("Request to get all settings.");
	}
	
	@GetMapping("/{name}")
	public void getSetting(@PathVariable String name) {
		logger.info("Request to get setting with name: " + name);
	}
	
	@PatchMapping
	public void updateSettings() {
		logger.info("Request to update settings.");
	}
}
