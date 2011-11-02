package portal.login.dao;

// Generated 14-giu-2011 21.19.05 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import portal.login.domain.Idp;

/**
 * Home object for domain model class Idp.
 * @see portal.login.dao.Idp
 * @author Hibernate Tools
 */
@Service("idpDAO")
@Transactional
public class IdpDAO {

	private static final Log log = LogFactory.getLog(IdpDAO.class);

	@Resource(name="sessionFactory")
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
	
	public String findByIdp(Idp idp) {
		log.debug("getting Idp instance");
		try {
			Session session = sessionFactory.getCurrentSession();
			Idp instance = (Idp) session.get(Idp.class, idp.getIdIdp());
			log.debug("get successful");
			return instance.getIdpname();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Idp> getAllIdp (){
		log.debug("getting all Idp instance");
		try {
			
			Session session = sessionFactory.getCurrentSession();
			
			// Create a Hibernate query (HQL)
			Query query = session.createQuery("FROM  Idp");

			// Retrieve all
			return  query.list();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
