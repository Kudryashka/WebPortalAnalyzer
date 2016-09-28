package name.dimasik.dev.web.portalanalyzer.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Set of methods for working with Hibernate.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class HibernateUtils {

	/**
	 * Execute some job with transaction management if it required.
	 * @param job The job.
	 */
	public static void processPersistentJob(SessionFactory factory, PersistentJob job) {
		Session session = null;
		try {
			session = factory.getCurrentSession();
		} catch(HibernateException e) { }
		
		if (session != null && session.isOpen()) {
			//Spring or something else manage transactions
			job.execute(session);
		} else {
			//We should manage transactions
			session = factory.openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				job.execute(session);
				tx.commit();
			} catch (Exception e) {
				if (tx != null) tx.rollback();
				throw e;
			} finally {
				session.close();
			}
		}
	}
	
	/**
	 * Interface to define executable persistent job.
	 *
	 * @author Dmytro Kudria
	 * @author <a href="http://dimasik.name">http://dimasik.name</a>
	 *
	 */
	public interface PersistentJob {
		
		/**
		 * Job actions should be implement inside this method.
		 * @param session Opened session.
		 */
		void execute (Session session);
	}
}
