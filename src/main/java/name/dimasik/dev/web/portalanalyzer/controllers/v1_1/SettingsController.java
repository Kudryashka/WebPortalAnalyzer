package name.dimasik.dev.web.portalanalyzer.controllers.v1_1;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import name.dimasik.dev.web.portalanalyzer.settings.Preference;
import name.dimasik.dev.web.portalanalyzer.settings.SettingsProvider;

/**
 * Handles requests to provide and update settings.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@RestController("SettingsController_v1.1")
@RequestMapping("/api/v1.1/settings")
public class SettingsController implements ExceptionHandledController {

	private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);
	
	private SettingsProvider settingsService;

	/**
	 * Used for injection of {@link SettingsProvider}
	 */
	@Autowired
	public void setSettingsService(SettingsProvider settingsService) {
		this.settingsService = settingsService;
	}
	
	/**
	 * Retrieve all settings
	 */
	@GetMapping
	public List<JsonSettingEntity> getAllSettings() {
		logger.info("getSettings()");
		List<JsonSettingEntity> result = new ArrayList<>();
		
		for (Preference pref: Preference.values()) {
			result.add(getSetting(pref.name()));
		}
		
		return result;
	}
	
	/**
	 * Get a setting by the name
	 */
	@GetMapping("/{name}")
	public JsonSettingEntity getSetting(@PathVariable String name) {
		logger.info("Get setting by the name: " + name);
		String value = settingsService.getSettingAsString(name);
		
		JsonSettingEntity result = new JsonSettingEntity();
		result.setName(name);
		result.setValue(value);
		
		return result;
	}
	
	/**
	 * Update some settings
	 */
	@PatchMapping
	public void updateSettings(@RequestBody JsonSettingEntity[] settings) {
		logger.info("updateSettings()");
		for (JsonSettingEntity s : settings) {
			settingsService.updateSettingByName(s.getName(), s.getValue());
		}
	}
	
	
	/**
	 * JSON view representation for Setting entity.
	 */
	public static class JsonSettingEntity {
		
		private String name;
		private String type;
		private String value;
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		public String getType() {
			return type;
		}
		
		public void setType(String type) {
			this.type = type;
		}
	}
}
