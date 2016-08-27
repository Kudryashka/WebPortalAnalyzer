package name.dimasik.dev.web.portalanalyzer.userinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@Entity(name = "UserInfo")
@Table(name = "users_infos")
public class PersistentUserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private int id;
	
	@Column(name = "request_date", nullable = false)
	private Date requestDate;
	
	@Column(name = "user_ip", length = 30)
	private String userIp;
	
	@Column(name = "user_device", length = 10)
	private String userDevice;
	
	@Column(name = "user_platform", length = 50)
	private String userPlatform;
	
	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	private Double longitude;
	
	@Column(name = "city", length = 50)
	private String city;
	
	@Column(name = "region", length = 250)
	private String reqion;
	
	@Column(name = "country", length = 50)
	private String country;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUserDevice() {
		return userDevice;
	}

	public void setUserDevice(String userDevice) {
		this.userDevice = userDevice;
	}

	public String getUserPlatform() {
		return userPlatform;
	}

	public void setUserPlatform(String userPlatform) {
		this.userPlatform = userPlatform;
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

	public String getReqion() {
		return reqion;
	}

	public void setReqion(String reqion) {
		this.reqion = reqion;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
