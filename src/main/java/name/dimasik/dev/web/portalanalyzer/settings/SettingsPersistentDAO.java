package name.dimasik.dev.web.portalanalyzer.settings;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@Repository
public class SettingsPersistentDAO implements SettingsDAO {

	private static final Logger logger = LoggerFactory.getLogger(SettingsPersistentDAO.class);
	
	private SessionFactory sessionFactory;
	
	/**
	 * TODO
	 * @param sessionFactory
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		logger.info("Session factory initialized");
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void updateSettings(List<Setting> settings) {
		logger.info("Update request received");
		Session session = sessionFactory.getCurrentSession();
		for (Setting s : settings) {
			session.update(s);
		}
	}

	@Override
	public Setting getSetting(String name) {
		logger.info("Get request received");
		Session session = sessionFactory.getCurrentSession();
		Query<Setting> query = session.createQuery("from Setting where name=:name", Setting.class);
		query.setParameter("name", name);
		
		Optional<Setting> optionalSetting = query.uniqueResultOptional();
		return optionalSetting.orElse(null);
	}

	@Override
	public List<Setting> getAllSettings() {
		logger.info("GetAll request received");
		Session session = sessionFactory.getCurrentSession();
		List<Setting> allSettings = session.createQuery("from Setting", Setting.class).stream().collect(Collectors.toList());
		return allSettings;
	}
}
