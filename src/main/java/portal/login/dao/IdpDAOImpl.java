package portal.login.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import portal.login.domain.Idp;

@Repository
public class IdpDAOImpl implements IdpDAO {

	private static final Logger log = Logger.getLogger(IdpDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void save(Idp transientInstance) {
		log.debug("persisting Idp instance");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void delete(Idp persistentInstance) {
		log.debug("removing Idp instance");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.delete(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Idp findById(Integer id) {
		log.debug("getting Idp instance with id: " + id);
		try {
			Session session = sessionFactory.getCurrentSession();
			Idp instance = (Idp) session.get(Idp.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Idp> getAllIdp() {
		log.debug("getting all Idp instance");
		try {

			Session session = sessionFactory.getCurrentSession();

			// Create a Hibernate query (HQL)
			Query query = session.createQuery("FROM  Idp");

			// Retrieve all
			return query.list();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

}
