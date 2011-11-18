package portal.login.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import portal.login.domain.Vo;

@Repository
public class VoDAOImpl implements VoDAO {

	private static final Logger log = Logger
			.getLogger(VoDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Vo> getAllVo() {
		log.debug("getting all Vo instance");
		try {

			Session session = sessionFactory.getCurrentSession();

			// Create a Hibernate query (HQL)
			Query query = session.createQuery("FROM  Vo ORDER BY vo");

			// Retrieve all
			return query.list();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public Vo findById(Integer id) {
		log.debug("getting UserInfo instance with id: " + id);
		try {
			Session session = sessionFactory.getCurrentSession();
			Vo instance = (Vo) session.get(Vo.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getAllDiscipline() {
		log.debug("getting all Vo instance");
		try {

			Session session = sessionFactory.getCurrentSession();

			// Create a Hibernate query (HQL)
			Query query = session
					.createSQLQuery("SELECT DISTINCT Discipline FROM  VO ORDER BY Discipline asc");

			// Retrieve all
			return query.list();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Vo> getAllVoByName(String search) {
		log.debug("getting all Vo instance");
		try {

			Session session = sessionFactory.getCurrentSession();

			// Create a Hibernate query (HQL)
			Query query = session.createQuery("FROM  Vo WHERE vo LIKE '%"+search+"%' ORDER BY vo");

			// Retrieve all
			return query.list();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

}
