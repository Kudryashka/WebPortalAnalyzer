package name.dimasik.dev.web.portalanalyzer.checklink;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import name.dimasik.dev.web.portalanalyzer.util.HibernateUtils;
import name.dimasik.dev.web.portalanalyzer.util.HibernateUtils.PersistentJob;

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
	public List<LinksCheck> getLinksChecks(int days) {
		List<LinksCheck> result = new ArrayList<>();
		
		HibernateUtils.processPersistentJob(sessionFactory, new PersistentJob() {
			@Override
			public void execute(Session session) {
				session.createQuery("from LinksCheck", PersistentLinksCheck.class)
					.stream().collect(Collectors.toList())
					.forEach(check -> result.add(check));	
			}
		});
		
		//TODO tmp
		result.forEach(check -> check.setCheckedLinks(getCheckDetails(check.getId())));
		
		return result;
	}

	@Override
	public void addLinksCheck(LinksCheck check) {
		if (check instanceof PersistentLinksCheck) {
			PersistentLinksCheck persistentCheck = (PersistentLinksCheck) check;
			
			HibernateUtils.processPersistentJob(sessionFactory, new PersistentJob() {
				@Override
				public void execute(Session session) {
					int checkId = (int) session.save(check);
					for (PersistentCheckedLink link : persistentCheck.getCheckedLinks()) {
						link.setCheckId(checkId);
						session.persist(link);
					}
				}
			});
			
		} else {
			throw new IllegalArgumentException("Illegal type of argument");
		}
	}

	@Override
	public void updateLinksCheck(LinksCheck check) {
		//TODO
		if (check instanceof PersistentLinksCheck) {
			PersistentLinksCheck persistentCheck = (PersistentLinksCheck) check;
			sessionFactory.getCurrentSession().update(persistentCheck);
		} else {
			throw new IllegalArgumentException("Illegal type of argument");
		}
	}

	@Override
	public List<CheckedLink> getCheckDetails(int checkId) {
		List<CheckedLink> result = new ArrayList<>();
		HibernateUtils.processPersistentJob(sessionFactory, new PersistentJob() {
			@Override
			public void execute(Session session) {
				Query<CheckedLink> query = session.createQuery("from CheckedLink where checkId=:checkid", CheckedLink.class);
				query.setParameter("checkid", checkId);
				query.stream().collect(Collectors.toList()).forEach(l -> result.add(l));
			}
		});
		return result;
	}
}
