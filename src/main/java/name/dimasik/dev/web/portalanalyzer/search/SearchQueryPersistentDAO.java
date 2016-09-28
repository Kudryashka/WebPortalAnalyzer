package name.dimasik.dev.web.portalanalyzer.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import name.dimasik.dev.web.portalanalyzer.util.Pair;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@Repository("SearchQueryPersistentDAO")
public class SearchQueryPersistentDAO implements SearchQueriesDAO {

	private Logger logger = LoggerFactory.getLogger(SearchQueryPersistentDAO.class);
	
	private SessionFactory sessionFactory;
	
	/**
	 * Using for session factory injection
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		logger.info("SessionFactory initialized");
	}

	@Override
	public List<Pair<String, Date>> getSearchQueries(int days) {
		logger.info("Process get request");
		Session session = sessionFactory.getCurrentSession();
		
		//TODO days
		List<PersistentQueryObject> persistentObjects = 
				session.createQuery("from SearchQuery", PersistentQueryObject.class)
					.stream().collect(Collectors.toList());
		
		List<Pair<String, Date>> result = new ArrayList<>();
		for (PersistentQueryObject obj : persistentObjects) {
			result.add(new Pair<String, Date>(obj.getQueryBody(), obj.getQueryDate()));
		}
		
		return result;
	}

	@Override
	public void addSearchQuery(String query, Date date) {
		logger.info("Process insert request");
		
		PersistentQueryObject q = new PersistentQueryObject();
		q.setQueryBody(query);
		q.setQueryDate(date);
		
		Session session = sessionFactory.getCurrentSession();
		session.persist(q);
	}
}
