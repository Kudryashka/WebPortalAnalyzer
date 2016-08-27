package name.dimasik.dev.web.portalanalyzer.userinfo;

import com.fasterxml.jackson.annotation.JsonView;

import name.dimasik.dev.web.portalanalyzer.controllers.v1_0.UsersInfoView;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class UserRequestInfo {

	@JsonView(UsersInfoView.class)
	private String ip;
	
	@JsonView(UsersInfoView.class)
	private Double latitude;
	
	@JsonView(UsersInfoView.class)
	private Double longitude;
	
	@JsonView(UsersInfoView.class)
	private String city;
	
	@JsonView(UsersInfoView.class)
	private String region;
	
	@JsonView(UsersInfoView.class)
	private String country;
	
	@JsonView(UsersInfoView.class)
	private String deviceType;
	
	@JsonView(UsersInfoView.class)
	private String devicePlatform;
	
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

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDevicePlatform() {
		return devicePlatform;
	}

	public void setDevicePlatform(String devicePlatform) {
		this.devicePlatform = devicePlatform;
	}
}
