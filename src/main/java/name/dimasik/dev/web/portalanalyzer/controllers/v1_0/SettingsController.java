package name.dimasik.dev.web.portalanalyzer.controllers.v1_0;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import name.dimasik.dev.web.portalanalyzer.settings.Preference;
import name.dimasik.dev.web.portalanalyzer.settings.SettingsProvider;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@RestController
@RequestMapping("/api/v1.0/portalSettings")
public class SettingsController {
	
	private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);
	
	private SettingsProvider settingsProvider;
	
	@Autowired
	public void setSettingsProvider(SettingsProvider settingsProvider) {
		logger.info("Settings service initialized");
		this.settingsProvider = settingsProvider;
	}

	@GetMapping
	public List<SettingsEntity> getAllSettings() {
		logger.info("Request to get all settings.");
		List<SettingsEntity> result = new ArrayList<>();
		
		for (Preference pref: Preference.values()) {
			result.add(getSetting(pref.name()));
		}
		
		return result;
	}
	
	@GetMapping("/{name}")
	public SettingsEntity getSetting(@PathVariable String name) {
		logger.info("Request to get setting with name: " + name);
		String value = settingsProvider.getSettingAsString(name);
		
		SettingsEntity result = new SettingsEntity();
		result.setName(name);
		result.setValue(value);
		
		return result;
	}
	
	@PatchMapping
	public void updateSettings(@RequestBody SettingsEntity[] settings) {
		logger.info("Request to update settings.");
		for (SettingsEntity s : settings) {
			settingsProvider.updateSettingByName(s.getName(), s.getValue());
		}
	}
}
