package name.dimasik.dev.web.portalanalyzer.checklink;

import java.util.Date;
import java.util.List;

/**
 * Represents links check as DAO object.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 */
public interface LinksCheck {

	/**
	 * Get ID of the links check.
	 * @return The ID.
	 */
	int getId();
	
	/**
	 * Get start time of the links check.
	 * @return The start time.
	 */
	Date getStartTime();
	
	/**
	 * Get end time of the links check.
	 * @return The end time.
	 */
	Date getEndTime();
	
	/**
	 * Get links checked by this check.
	 * @return The list of the checked links.
	 */
	List<? extends CheckedLink> getCheckedLinks();
	
	/**
	 * Set ID of the links check.
	 * @param id The id.
	 */
	void setId(int id);
	
	/**
	 * Set start time of the links check.
	 * @param startTime The start time.
	 */
	void setStartTime(Date startTime);
	
	/**
	 * Set end time of the links check.
	 * @param endTime The end time.
	 */
	void setEndTime(Date endTime);
	
	/**
	 * Set links checked by this check.
	 * @param links The list of the checked links.
	 */
	void setCheckedLinks(List<? extends CheckedLink> links);
}
