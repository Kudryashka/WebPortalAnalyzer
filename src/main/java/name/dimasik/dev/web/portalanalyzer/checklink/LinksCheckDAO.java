package name.dimasik.dev.web.portalanalyzer.checklink;

import java.util.List;

/**
 * Interface for DAO (Data Access Object) for links checks.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 */
public interface LinksCheckDAO {

	/**
	 * Get links check for specified period. 
	 * @param days Count of days before today.
	 * @return List of links checks for period.
	 */
	List<LinksCheck> getLinksChecks(int days);
	
	/**
	 * Add new links check to be saved.
	 * @param check The {@link LinksCheck}.
	 */
	void addLinksCheck(LinksCheck check);
	
	/**
	 * Update existing links check.
	 * @param check The {@link LinksCheck}
	 */
	void updateLinksCheck(LinksCheck check);
	
	
	/**
	 * Get detail information about the check with specified identifier.
	 * @param checkId Check identifier.
	 * @return List of checked links during the check.
	 */
	List<CheckedLink> getCheckDetails(int checkId);
}
