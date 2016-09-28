package name.dimasik.dev.web.portalanalyzer.checklink;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Represents {@link LinksCheck} implementation for persistent layer.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@Entity(name = "LinksCheck")
@Table(name = "links_check")
public class PersistentLinksCheck implements LinksCheck {

	/**
	 * Initialize new object from {@link LinkInfo} list.
	 * @param infos List of {@link LinkInfo}
	 * @return New instance of {@link PersistentLinksCheck}
	 */
	public static LinksCheck fromLinkInfos(Date start, Date end, List<LinkInfo> infos) {
		PersistentLinksCheck linksCheck = new PersistentLinksCheck();
		
		List<CheckedLink> checkedLinks = new ArrayList<>();
		for (LinkInfo info : infos) {
			CheckedLink link = new PersistentCheckedLink();
			link.setLinkStatus(info.linkStatus);
			link.setLinkType(info.type);
			link.setLocation(info.pageUrl);
			link.setRedirectUrl(info.redirectTarget);
			link.setResponseCode(info.responseCode);
			link.setTarget(info.targetUrl);
			checkedLinks.add(link);
		}
		
		linksCheck.setStartTime(start);
		linksCheck.setEndTime(end);
		linksCheck.setCheckedLinks(checkedLinks);
		
		return linksCheck;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private int id;
	
	@Column(name = "start_time", nullable = false)
	private Date startTime;
	
	@Column(name = "end_time")
	private Date endTime;
	
	//TODO
	@Transient
	private List<PersistentCheckedLink> checkedLinks;
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public Date getStartTime() {
		return startTime;
	}

	@Override
	public Date getEndTime() {
		return endTime;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Override
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public List<PersistentCheckedLink> getCheckedLinks() {
		return checkedLinks;
	}

	@Override
	public void setCheckedLinks(List<? extends CheckedLink> links) {
		this.checkedLinks = (List<PersistentCheckedLink>) links;
	}
}
