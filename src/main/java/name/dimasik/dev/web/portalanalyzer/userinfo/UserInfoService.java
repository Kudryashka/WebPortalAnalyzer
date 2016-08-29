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
import org.springframework.web.client.RestTemplate;

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
	@Transactional
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
		
		RestTemplate restTemplate = new RestTemplate();
		RestServiceIpInfoResult ipInfoRestResult = restTemplate.getForObject("http://ipinfo.io/{ip}/json"
				, RestServiceIpInfoResult.class, ipAddress);
		
		if (ipInfoRestResult.loc == null) {
			logger.error("Received location from REST service is null! IP: " + ipAddress);
			return;
		}
		
		String[] locSplit = ipInfoRestResult.loc.split(",");
		if (locSplit.length != 2) {
			logger.error("Received location from REST service is incorrect! Location: " + ipInfoRestResult.loc + " IP: " + ipAddress);
			return;
		}
		
		Double latitude = null;
		Double longitude = null;
		
		try {
			latitude = Double.valueOf(locSplit[0].trim());
			longitude = Double.valueOf(locSplit[1].trim());
		} catch (NumberFormatException e) {
			logger.error("Received location from REST service is incorrect! Location: " + ipInfoRestResult.loc + " IP: " + ipAddress);
			latitude = longitude = null;
		}
		
		UserRequestInfo info = new UserRequestInfo();
		info.setIp(ipAddress);
		info.setDeviceType(deviceType.name());
		info.setDevicePlatform(devicePlatform.name());
		info.setLatitude(latitude);
		info.setLongitude(longitude);
		info.setCity(ipInfoRestResult.city);
		info.setRegion(ipInfoRestResult.region);
		info.setCountry(ipInfoRestResult.country);
		
		registerUserInfo(info);
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
	
	@Transactional
	public UserInfoReport getReport(int days) {
		//TODO
		return new UserInfoReport(getUsersInfos(days));
	}
	
	/**
	 * TODO add description
	 *
	 * @author Dmytro Kudria
	 * @author <a href="http://dimasik.name">http://dimasik.name</a>
	 *
	 */
	private static class RestServiceIpInfoResult {
		
		private String ip;
		private String city;
		private String region;
		private String country;
		private String loc;
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getLoc() {
			return loc;
		}
		public void setLoc(String loc) {
			this.loc = loc;
		}
	}
}
