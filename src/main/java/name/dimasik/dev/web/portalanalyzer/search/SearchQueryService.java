package name.dimasik.dev.web.portalanalyzer.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import name.dimasik.dev.web.portalanalyzer.util.Pair;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@Service
public class SearchQueryService {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchQueryService.class);

	private SearchQueriesDAO dao;
	
	@Autowired
	public void setDao(SearchQueriesDAO dao) {
		this.dao = dao;
	}

	/**
	 * TODO
	 */
//	@Transactional
	public List<QueryCountPair> getQueries(int days) {
		List<Pair<String, Date>> queries = dao.getSearchQueries(days);
		List<QueryCountPair> result = new ArrayList<>();
		for (Pair<String, Date> q : queries) {
			QueryCountPair pairInResult = null;
			for (QueryCountPair pair : result) {
				if (pair.getQuery().equals(q.getFirst())) {
					pairInResult = pair;
					break;
				}
			}
			if (pairInResult == null) {
				pairInResult = new QueryCountPair(q.getFirst());
				result.add(pairInResult);
			} else {
				pairInResult.setCount(pairInResult.getCount() + 1);
			}
		}
		return result.stream()
				.sorted((q1,q2) -> Integer.compare(q2.getCount(), q1.getCount()))
				.collect(Collectors.toList());
	}
	
	/**
	 * TODO
	 */
//	@Transactional
	public void registerQuery(String query) {
		dao.addSearchQuery(query, new Date());
	}
}
