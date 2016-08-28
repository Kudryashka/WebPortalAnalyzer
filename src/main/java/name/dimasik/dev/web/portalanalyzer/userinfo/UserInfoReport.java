package name.dimasik.dev.web.portalanalyzer.userinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonView;

import name.dimasik.dev.web.portalanalyzer.controllers.v1_0.UsersInfoView;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class UserInfoReport {
	
	@JsonView(UsersInfoView.class)
	private final GeneralSummary summary = new GeneralSummary();
	
	@JsonView(UsersInfoView.class)
	private final List<DeviceSummary> mobilePlatforms = new ArrayList<>();
	
	@JsonView(UsersInfoView.class)
	private final List<GeolocationSummary> geolocations = new ArrayList<>();
	
	/**
	 * TODO 
	 */
	public UserInfoReport(List<UserRequestInfo> infos) {
		summary.requestsCount = infos.size();
		int androidPlatformCount = 0;
		int iosPlatformCount = 0;
		int otherPlatformCount = 0;

		for (UserRequestInfo info : infos) {
			String deviceType = info.getDeviceType();
			boolean isMobilePlatform = false;
			if ("MOBILE".equals(deviceType)) {
				summary.mobileCount++;
				isMobilePlatform = true;
			} else if ("TABLET".equals(deviceType)) {
				summary.tabletCount++;
				isMobilePlatform = true;
			}
			if (isMobilePlatform) {
				String mobilePlatform = info.getDevicePlatform();
				if ("ANDROID".equals(mobilePlatform)) {
					androidPlatformCount++;
				} else if ("IOS".equals(mobilePlatform)) {
					iosPlatformCount++;
				} else {
					otherPlatformCount++;
				}
			}
		}
		
		if (androidPlatformCount > 0) {
			DeviceSummary androidPlatformSummary = new DeviceSummary();
			androidPlatformSummary.type = "ANDROID";
			androidPlatformSummary.requests = androidPlatformCount;
			mobilePlatforms.add(androidPlatformSummary);
		}
		if (iosPlatformCount > 0) {
			DeviceSummary iosPlatformSummary = new DeviceSummary();
			iosPlatformSummary.type = "IOS";
			iosPlatformSummary.requests = iosPlatformCount;
			mobilePlatforms.add(iosPlatformSummary);
		}
		if (otherPlatformCount > 0) {
			DeviceSummary otherPlatformSummary = new DeviceSummary();
			otherPlatformSummary.type = "OTHER";
			otherPlatformSummary.requests = otherPlatformCount;
			mobilePlatforms.add(otherPlatformSummary);
		}
		
		for (UserRequestInfo info : infos) {
			boolean isNeedNew = true;
			Double infoLatitude = info.getLatitude();
			Double infoLongitude = info.getLongitude();
			String infoCity = info.getCity();
			String infoRegion = info.getRegion();
			String infoCountry = info.getCountry();
			for (GeolocationSummary geo : geolocations) {
				if (Objects.equals(geo.latitude, infoLatitude)
						&& Objects.equals(geo.longitude, infoLongitude)
						&& Objects.equals(geo.city, infoCity)
						&& Objects.equals(geo.reqion, infoRegion)
						&& Objects.equals(geo.country, infoCountry)) {
					geo.requestsCount++;
					isNeedNew = false;
					break;
				}
			}
			if (isNeedNew) {
				GeolocationSummary newGeoSummary = new GeolocationSummary();
				newGeoSummary.requestsCount = 1;
				newGeoSummary.latitude = infoLatitude;
				newGeoSummary.longitude = infoLongitude;
				newGeoSummary.city = infoCity;
				newGeoSummary.reqion = infoRegion;
				newGeoSummary.country = infoCountry;
				geolocations.add(newGeoSummary);
			}
		}
	}

	public GeneralSummary getSummary() {
		return summary;
	}

	public List<DeviceSummary> getMobilePlatforms() {
		return mobilePlatforms;
	}

	public List<GeolocationSummary> getGeolocations() {
		return geolocations;
	}

	public static class GeneralSummary {
		
		@JsonView(UsersInfoView.class)
		private int requestsCount;
		
		@JsonView(UsersInfoView.class)
		private int mobileCount;
		
		@JsonView(UsersInfoView.class)
		private int tabletCount;

		public int getRequestsCount() {
			return requestsCount;
		}

		public int getMobileCount() {
			return mobileCount;
		}

		public int getTabletCount() {
			return tabletCount;
		}
	}
	
	public static class DeviceSummary {
		
		@JsonView(UsersInfoView.class)
		private String type;
		
		@JsonView(UsersInfoView.class)
		private int requests;

		public String getType() {
			return type;
		}

		public int getRequests() {
			return requests;
		}
	}
	
	public static class GeolocationSummary {
		
		@JsonView(UsersInfoView.class)
		private int requestsCount;
		
		@JsonView(UsersInfoView.class)
		private Double latitude;
		
		@JsonView(UsersInfoView.class)
		private Double longitude;
		
		@JsonView(UsersInfoView.class)
		private String city;
		
		@JsonView(UsersInfoView.class)
		private String reqion;
		
		@JsonView(UsersInfoView.class)
		private String country;

		public int getRequestsCount() {
			return requestsCount;
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

		public String getReqion() {
			return reqion;
		}

		public String getCountry() {
			return country;
		}
	}
}
