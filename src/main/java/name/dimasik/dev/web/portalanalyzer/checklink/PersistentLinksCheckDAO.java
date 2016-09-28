package name.dimasik.dev.web.portalanalyzer.checklink;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
	public List<LinksCheck> getLinksChecks(int days) {
		Session session = null;
//		try {
			session = sessionFactory.getCurrentSession();
//		} catch (HibernateException e) {
//			session = sessionFactory.openSession();
//		}
//		if (!session.isOpen()) {
//			session = sessionFactory.openSession();
//		}
		
		List<LinksCheck> result = new ArrayList<>();
		
		//TODO
//		for (LinksCheck check : result) {
//			for (CheckedLink l : check.getCheckedLinks()) {
//				Hibernate.initialize(l);
//			}
//		}
		
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
//			
			List<PersistentLinksCheck> linksChecks = session
					.createQuery("from LinksCheck", PersistentLinksCheck.class)
					.stream().collect(Collectors.toList());
//			tx.commit();
			// TODO
			
			
			linksChecks.forEach(check -> result.add(check));
			
//		} finally {
//			session.close();
//		}
		
		return result;
	}

	@Override
	public void addLinksCheck(LinksCheck check) {
		if (check instanceof PersistentLinksCheck) {
			PersistentLinksCheck persistentCheck = (PersistentLinksCheck) check;
			//TODO
			Session session = null;
			try {
				session = sessionFactory.getCurrentSession(); 
			} catch(HibernateException e) {
				session = sessionFactory.openSession();
			}
			if (!session.isOpen()) {
				session = sessionFactory.openSession();
			}
			
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				session.persist(persistentCheck);
				for (PersistentCheckedLink link : persistentCheck.getCheckedLinks()) {
					session.persist(link);
				}
				tx.commit();
			} catch(Exception e) {
				if (tx != null) tx.rollback();
				throw e;
			} finally {
				session.close();
			}
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
