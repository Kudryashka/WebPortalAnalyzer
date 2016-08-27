package name.dimasik.dev.web.portalanalyzer.userinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class UserInfoPersistentDAO implements UserInfoDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoPersistentDAO.class);
	
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
	public void registerUserInfo(UserRequestInfo info, Date date) {
		logger.info("Request to register info");
		
		PersistentUserInfo persistentInfo = new PersistentUserInfo();
		persistentInfo.setRequestDate(date);
		
		persistentInfo.setUserIp(info.getIp());
		persistentInfo.setUserDevice(info.getDeviceType());
		persistentInfo.setUserPlatform(info.getDevicePlatform());
		persistentInfo.setLatitude(info.getLatitude());
		persistentInfo.setLongitude(info.getLongitude());
		persistentInfo.setCity(info.getCity());
		persistentInfo.setReqion(info.getRegion());
		persistentInfo.setCountry(info.getCountry());
		
		Session session = sessionFactory.getCurrentSession();
		session.persist(persistentInfo);
	}

	@Override
	public List<UserRequestInfo> getUsersInfos(int days) {
		logger.info("Request to get infos");
		
		//TODO days
		
		Session session = sessionFactory.getCurrentSession();
		List<PersistentUserInfo> infos = session.createQuery("from UserInfo", PersistentUserInfo.class)
				.stream().collect(Collectors.toList());
		
		List<UserRequestInfo> result = new ArrayList<>();
		for (PersistentUserInfo p : infos) {
			UserRequestInfo i = new UserRequestInfo();
			i.setIp(p.getUserIp());
			i.setDeviceType(p.getUserDevice());
			i.setDevicePlatform(p.getUserPlatform());
			i.setLatitude(p.getLatitude());
			i.setLongitude(p.getLongitude());
			i.setCity(p.getCity());
			i.setRegion(p.getReqion());
			i.setCountry(p.getCountry());
			result.add(i);
		}
		
		return result;
	}
}
