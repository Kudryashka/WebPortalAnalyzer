package name.dimasik.dev.web.portalanalyzer.checklink;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@Repository
@Deprecated
public class CheckLinksDAO {
	
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	//TODO tmp
	private HashMap<Date, List<LinkInfo>> results = new HashMap<>();

	public void saveCheckLinksResult(Date date, List<LinkInfo> infos) {
		results.clear();
		results.put(date, infos);
	}
	
	public HashMap<Date, List<LinkInfo>> getResults() {
		return results;
	}
}
