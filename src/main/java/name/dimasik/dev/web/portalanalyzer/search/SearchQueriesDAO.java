package name.dimasik.dev.web.portalanalyzer.search;

import java.util.Date;
import java.util.List;

import name.dimasik.dev.web.portalanalyzer.util.Pair;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public interface SearchQueriesDAO {

	/**
	 * TODO
	 */
	public List<Pair<String, Date>> getSearchQueries(int days);
	
	/**
	 * TODO
	 */
	public void addSearchQuery(String query, Date date);
}
