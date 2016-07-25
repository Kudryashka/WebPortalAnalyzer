package name.dimasik.dev.web.portalanalyzer.userinfo;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);
	
	private enum DeviceType {
		NORMAL,
		MOBILE,
		TABLET
	}

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
}
