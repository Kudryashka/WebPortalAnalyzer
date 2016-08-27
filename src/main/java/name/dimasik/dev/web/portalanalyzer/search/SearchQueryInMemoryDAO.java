package name.dimasik.dev.web.portalanalyzer.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import name.dimasik.dev.web.portalanalyzer.util.Pair;

/**
 * TODO add description
 * TODO REMOVE IT
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@Component("SearchQueryInMemoryDAO")
public class SearchQueryInMemoryDAO implements SearchQueriesDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchQueryInMemoryDAO.class);
	
	private List<Pair<String, Date>> queries = new ArrayList<>();

	/* (non-Javadoc)
	 * @see name.dimasik.dev.web.portalanalyzer.search.SearchQueriesDAO#getSearchQueries(int)
	 */
	@Override
	public synchronized List<Pair<String, Date>> getSearchQueries(int days) {
		Date from = new Date(new Date().getTime() - days * 24 * 60 * 60 * 1000);
		//TODO don't return anything for 30 days
//		return queries.stream().filter(p -> (p.getSecond().compareTo(from) > 0)).collect(Collectors.toList());
		return queries;
	}

	/* (non-Javadoc)
	 * @see name.dimasik.dev.web.portalanalyzer.search.SearchQueriesDAO#addSearchQuery(java.lang.String, java.util.Date)
	 */
	@Override
	public synchronized void addSearchQuery(String query, Date date) {
		queries.add(new Pair<String, Date>(query, date));
	}

}
