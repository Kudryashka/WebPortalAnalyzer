package name.dimasik.dev.web.portalanalyzer.search;

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
@Entity(name = "SearchQuery")
@Table(name = "queries")
public class PersistentQueryObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private int id;
	
	@Column(name = "query_date", nullable = false)
	private Date queryDate;
	
	@Column(name = "query_body", columnDefinition = "TEXT NOT NULL")
	private String queryBody;

	/**
	 * TODO
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * TODO
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * TODO
	 * @return
	 */
	public Date getQueryDate() {
		return queryDate;
	}

	/**
	 * TODO
	 * @param queryDate
	 */
	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	/**
	 * TODO
	 * @return
	 */
	public String getQueryBody() {
		return queryBody;
	}

	/**
	 * TODO
	 * @param queryBody
	 */
	public void setQueryBody(String queryBody) {
		this.queryBody = queryBody;
	}
}
