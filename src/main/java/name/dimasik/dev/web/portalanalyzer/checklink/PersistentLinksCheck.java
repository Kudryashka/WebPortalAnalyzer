package name.dimasik.dev.web.portalanalyzer.checklink;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private int id;
	
	@Column(name = "start_time", nullable = false)
	private Date startTime;
	
	@Column(name = "end_time")
	private Date endTime;
	
	@OneToMany(mappedBy = "linksCheck", fetch = FetchType.LAZY)
	private List<CheckedLink> checkedLinks;
	
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
	public List<CheckedLink> getCheckedLinks() {
		return checkedLinks;
	}

	@Override
	public void setCheckedLinks(List<CheckedLink> links) {
		this.checkedLinks = links;
	}

}
