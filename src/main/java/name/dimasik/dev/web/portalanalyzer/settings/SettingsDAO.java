package name.dimasik.dev.web.portalanalyzer.settings;

import java.util.List;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public interface SettingsDAO {

	/**
	 * TODO
	 * @param settings
	 */
	public void updateSettings(List<Setting> settings);
	
	/**
	 * TODO
	 * @param name
	 */
	public Setting getSetting(String name);
	
	/**
	 * TODO
	 * @return
	 */
	public List<Setting> getAllSettings();
}
