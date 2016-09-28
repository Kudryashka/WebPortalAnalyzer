package name.dimasik.dev.web.portalanalyzer.checklink;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * {@link LinksCheckDAO} implementation for persistent layer.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@Repository("PersistentLinksCheckDAO")
public class PersistentLinksCheckDAO implements LinksCheckDAO {

	private SessionFactory sessionFactory;
	
	/**
	 * Used for session factory injection.
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<LinksCheck> getLinksCheck(int days) {
		Session session = sessionFactory.getCurrentSession();
		
		List<PersistentLinksCheck> linksChecks = session
				.createQuery("from LinksCheck", PersistentLinksCheck.class)
				.stream().collect(Collectors.toList());
		// TODO
		
		List<LinksCheck> result = new ArrayList<>();
		linksChecks.forEach(check -> result.add(check));
		
		return result;
	}

	@Override
	public void addLinksCheck(LinksCheck check) {
		if (check instanceof PersistentLinksCheck) {
			PersistentLinksCheck persistentCheck = (PersistentLinksCheck) check;
			sessionFactory.getCurrentSession().persist(persistentCheck);
		} else {
			throw new IllegalArgumentException("Illegal type of argument");
		}
	}

	@Override
	public void updateLinksCheck(LinksCheck check) {
		if (check instanceof PersistentLinksCheck) {
			PersistentLinksCheck persistentCheck = (PersistentLinksCheck) check;
			sessionFactory.getCurrentSession().update(persistentCheck);
		} else {
			throw new IllegalArgumentException("Illegal type of argument");
		}
	}
}
