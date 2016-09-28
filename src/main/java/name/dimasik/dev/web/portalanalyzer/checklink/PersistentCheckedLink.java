package name.dimasik.dev.web.portalanalyzer.checklink;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents {@link CheckedLink} implementation for persistent layer.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@Entity(name = "CheckedLink")
@Table(name = "checked_links")
public class PersistentCheckedLink implements CheckedLink {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private int id;
	
	@Column(name = "check_id", nullable = false)
	private int checkId;
	
	@Column(name = "link_status", nullable = false, length = 15)
	private LinkStatus linkStatus;
	
	@Column(name = "link_type", nullable = false, length = 15)
	private LinkType linkType;
	
	@Column(name = "target", columnDefinition = "TEXT")
	private String target;
	
	@Column(name = "location", columnDefinition = "TEXT")
	private String location;
	
	@Column(name = "response_code")
	private Integer responseCode;
	
	@Column(name = "redirect_url", columnDefinition = "TEXT")
	private String redirectUrl;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getCheckId() {
		return checkId;
	}

	@Override
	public LinkStatus getLinkStatus() {
		return linkStatus;
	}

	@Override
	public LinkType getLinkType() {
		return linkType;
	}

	@Override
	public String getTarget() {
		return target;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public Integer getResponseCode() {
		return responseCode;
	}

	@Override
	public String getRedirectUrl() {
		return redirectUrl;
	}

	//TODO
	@Override
	public void setCheckId(int id) {
		this.checkId = id;
	}

	@Override
	public void setLinkStatus(LinkStatus status) {
		this.linkStatus = status;
	}

	@Override
	public void setLinkType(LinkType type) {
		this.linkType = type;
	}

	@Override
	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}
