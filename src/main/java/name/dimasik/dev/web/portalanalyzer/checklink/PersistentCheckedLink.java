package name.dimasik.dev.web.portalanalyzer.checklink;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

	@ManyToOne
	@JoinColumn(name = "check_id", nullable = false)
	private LinksCheck linksCheck;
	
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
	
	@Override
	public LinksCheck getLinksCheck() {
		return linksCheck;
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

	@Override
	public void setLinksCheck(LinksCheck check) {
		this.linksCheck = check;
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
