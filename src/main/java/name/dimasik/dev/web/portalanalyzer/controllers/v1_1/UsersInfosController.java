package name.dimasik.dev.web.portalanalyzer.controllers.v1_1;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import name.dimasik.dev.web.portalanalyzer.userinfo.UserInfoReport;
import name.dimasik.dev.web.portalanalyzer.userinfo.UserInfoService;
import name.dimasik.dev.web.portalanalyzer.userinfo.UserRequestInfo;
import name.dimasik.dev.web.portalanalyzer.util.HttpUtils;
import name.dimasik.dev.web.portalanalyzer.util.Parser;
import name.dimasik.dev.web.portalanalyzer.util.Parser.DaysCountFormatException;

/**
 * This controller handles requests to process users statistic.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@RestController
@RequestMapping("/v1.1/usersInfos")
@CrossOrigin
public class UsersInfosController implements ExceptionHandledController {
	
	private final static Logger logger = LoggerFactory.getLogger(UsersInfosController.class);
	
	private UserInfoService userInfoService = new UserInfoService();
	
	/**
	 * User for injection of {@link UserInfoService}
	 */
	@Autowired
	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	/**
	 * Register new user info.
	 */
	@PutMapping
	public ResponseEntity<String> registerRequest(@RequestBody JsonUserInfo requestInfo) {
		logger.info("Request to register new user info.");
		
		//TODO do validation
		
		//TODO reimplement
		UserRequestInfo info = new UserRequestInfo();
		info.setIp(requestInfo.ip);
		info.setLatitude(requestInfo.latitude);
		info.setLongitude(requestInfo.longitude);
		info.setDeviceType(requestInfo.device);
		info.setDevicePlatform(requestInfo.platform);
		
		userInfoService.registerUserInfo(info);
		
		return ResponseEntity.ok("");
	}
	
	/**
	 * Utility method to simplify working with the WPA.
	 */
	@GetMapping("/raw")
	public ResponseEntity<String> registerRawRequest(HttpServletRequest request) {
		logger.info("Process raw user request.");
		userInfoService.processRawUserRequest(request);
		return ResponseEntity.ok("");
	}
	
	/**
	 * Utility method to get IP address from JS on the client side.
	 */
	@GetMapping("/ip")
	public ResponseEntity<JsonIP> getIP(HttpServletRequest request) {
		logger.info("Requested ip of the user.");
		JsonIP responseBody = new JsonIP(HttpUtils.getIP(request));
		return ResponseEntity.ok(responseBody);
	}
	
	/**
	 * User info report for <code>{days}</code> days.
	 */
	@GetMapping("/report/{days}")
	public ResponseEntity<JsonUserInfoReport> getReport(@PathVariable("days") String daysStr) 
			throws DaysCountFormatException {
		logger.info("Request to get report. Days count: " + daysStr);
		
		int days = Parser.parseDaysCount(daysStr);
		
		int total = 0, mobile = 0, tablet = 0;
		
		//TODO reimplement
		UserInfoReport serviceReport = userInfoService.getReport(days);
		total = serviceReport.getSummary().getRequestsCount();
		mobile = serviceReport.getSummary().getMobileCount();
		tablet = serviceReport.getSummary().getTabletCount();
		
		
		JsonUserInfoReport.Summary reportSummary = new JsonUserInfoReport.Summary(total, mobile, tablet);
		List<JsonUserInfoReport.MobilePlatformReport> reportMobilePlatforms = new ArrayList<>();
		List<JsonUserInfoReport.GeoLocation> reportGeolocations = new ArrayList<>();
		
		//TODO reimplement
		serviceReport.getMobilePlatforms().stream().forEach(
				mp -> reportMobilePlatforms.add(
						new JsonUserInfoReport.MobilePlatformReport(mp.getType(), mp.getRequests())));
		serviceReport.getGeolocations().stream().forEach(
				loc -> reportGeolocations.add(
						new JsonUserInfoReport.GeoLocation(loc.getRequestsCount(), loc.getLatitude(), 
								loc.getLongitude(), loc.getCity(), loc.getReqion(), loc.getCountry())));
		
		JsonUserInfoReport report = 
				new JsonUserInfoReport(reportSummary, reportMobilePlatforms, reportGeolocations);
		
		return ResponseEntity.ok(report);
	}
	
	/**
	 * JSON view representation for IP.
	 */
	public static class JsonIP {
		
		private final String ip;

		private JsonIP(String ip) {
			this.ip = ip;
		}
		
		public String getIp() {
			return ip;
		}
	}

	/**
	 * JSON view representation for user info.
	 */
	public static class JsonUserInfo {
		
		private String ip;
		private Double latitude;
		private Double longitude;
		private String device;
		private String platform;

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public Double getLatitude() {
			return latitude;
		}

		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}

		public Double getLongitude() {
			return longitude;
		}

		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}

		public String getDevice() {
			return device;
		}

		public void setDevice(String device) {
			this.device = device;
		}

		public String getPlatform() {
			return platform;
		}

		public void setPlatform(String platform) {
			this.platform = platform;
		}

		@Override
		public String toString() {
			return new StringBuilder().append(JsonUserInfo.class.getSimpleName())
					.append("[").append(System.identityHashCode(this)).append("]")
					.append("{")
					.append("ip: ").append(ip).append(",")
					.append("latitude: ").append(latitude).append(",")
					.append("longitude: ").append(longitude).append(",")
					.append("device: ").append(device).append(",")
					.append("platform: ").append(platform)
					.append("}").toString();
		}
	}

	/**
	 * JSON view representation for user info report.
	 */
	public static class JsonUserInfoReport {
		
		private final Summary summary;
		private final List<MobilePlatformReport> mobilePlatforms;
		private final List<GeoLocation> geolocations;
		
		private JsonUserInfoReport(Summary summary, List<MobilePlatformReport> platforms,
				List<GeoLocation> geolocations) {
			this.summary = summary;
			this.mobilePlatforms = platforms;
			this.geolocations = geolocations;
		}
		
		public Summary getSummary() {
			return summary;
		}

		public List<MobilePlatformReport> getMobilePlatforms() {
			return mobilePlatforms;
		}

		public List<GeoLocation> getGeolocations() {
			return geolocations;
		}
		
		@Override
		public String toString() {
			return new StringBuilder().append(JsonUserInfoReport.class.getSimpleName())
					.append("[").append(System.identityHashCode(this)).append("]{")
					.append("summary: ").append(summary).append(", ")
					.append("mobilePlatforms: ").append(mobilePlatforms).append(", ")
					.append("geolocations: ").append(geolocations)
					.append("}").toString();
		}

		/**
		 * JSON view representation for summary part of user info report.
		 */
		public static class Summary {
			
			private final int total;
			private final int mobile;
			private final int tablet;
			
			private Summary(int total, int mobile, int tablet) {
				this.total = total;
				this.mobile = mobile;
				this.tablet = tablet;
			}

			public int getTotal() {
				return total;
			}

			public int getMobile() {
				return mobile;
			}

			public int getTablet() {
				return tablet;
			}

			@Override
			public String toString() {
				return new StringBuilder().append(Summary.class.getSimpleName()).append("{")
						.append("total: ").append(total).append(", ")
						.append("mobile: ").append(mobile).append(", ")
						.append("tablet: ").append(tablet)
						.append("}").toString();
			}
		}
		
		/**
		 * JSON view representation for mobile platform part of user info report.
		 */
		public static class MobilePlatformReport {
			
			private final String type;
			private final int requests;
			
			private MobilePlatformReport(String type, int requests) {
				this.type = type;
				this.requests = requests;
			}
			
			public String getType() {
				return type;
			}
			
			public int getRequests() {
				return requests;
			}

			@Override
			public String toString() {
				return new StringBuilder().append(MobilePlatformReport.class.getSimpleName()).append("{")
						.append("type: ").append(type).append(", ")
						.append("requests: ").append(requests)
						.append("}").toString();
			}
		}
		
		/**
		 * JSON view representation for location part of user info report.
		 */
		public static class GeoLocation {
			
			private final int requests;
			private final Double latitude;
			private final Double longitude;
			private final String city;
			private final String region;
			private final String country;
			
			private GeoLocation(int requests, Double latitude, Double longitude, 
					String city, String region, String country) {
				this.requests = requests;
				this.latitude = latitude;
				this.longitude = longitude;
				this.city = city;
				this.region = region;
				this.country = country;
			}
			
			public int getRequests() {
				return requests;
			}
			
			public Double getLatitude() {
				return latitude;
			}
			
			public Double getLongitude() {
				return longitude;
			}
			
			public String getCity() {
				return city;
			}
			
			public String getRegion() {
				return region;
			}
			
			public String getCountry() {
				return country;
			}

			@Override
			public String toString() {
				return new StringBuilder().append(GeoLocation.class.getSimpleName()).append("{")
						.append("requests: ").append(requests).append(", ")
						.append("latitude: ").append(latitude).append(", ")
						.append("longitude: ").append(longitude).append(", ")
						.append("city: ").append(city).append(", ")
						.append("region: ").append(region).append(", ")
						.append("country: ").append(country)
						.append("}").toString();
			}
		}
	}
}
