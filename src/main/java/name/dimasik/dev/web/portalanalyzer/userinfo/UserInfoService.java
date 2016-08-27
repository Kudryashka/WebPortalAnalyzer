package name.dimasik.dev.web.portalanalyzer.userinfo;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@Service
public class UserInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);
	
	private enum DeviceType {
		/**
		 * TODO
		 */
		NORMAL,
		/**
		 * TODO
		 */
		MOBILE,
		/**
		 * TODO
		 */
		TABLET
	}
	
	private UserInfoDAO userInfoDAO;

	@Autowired
	public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
		logger.info("User info DAO initialize");
		this.userInfoDAO = userInfoDAO;
	}

	/**
	 * TODO
	 * @param request
	 */
	public void processRawUserRequest(HttpServletRequest request) {
		logger.info("processRawUserRequest()");
		
		Device device = DeviceUtils.getCurrentDevice(request);
		
		DeviceType deviceType;
		if (device.isMobile()) {
			deviceType = DeviceType.MOBILE;
		} else if (device.isTablet()) {
			deviceType = DeviceType.TABLET;
		} else { // normal - desktop or laptop
			deviceType = DeviceType.NORMAL;
		}
		logger.debug("Device type: " + deviceType);
		
		DevicePlatform devicePlatform = device.getDevicePlatform();
		logger.debug("Device platform: " + devicePlatform);
		
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		logger.debug("User IP address: " + ipAddress);
		
		//TODO determine location
	}
	
	/**
	 * TODO
	 * @param info
	 */
	@Transactional
	public void registerUserInfo(UserRequestInfo info) {
		userInfoDAO.registerUserInfo(info, new Date());
	}
	
	/**
	 * TODO
	 * @param days
	 * @return
	 */
	@Transactional
	public List<UserRequestInfo> getUsersInfos(int days) {
		return userInfoDAO.getUsersInfos(days);
	}
}
