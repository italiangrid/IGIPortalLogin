package portal.login.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import portal.login.domain.UserInfo;

@Repository
public class UserInfoDAOImpl implements UserInfoDAO {

	private static final Logger log = Logger.getLogger(UserInfoDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Integer save(UserInfo transientInstance) {
		log.debug("persisting UserInfo instance");
		try {
			Session session = sessionFactory.getCurrentSession();
			int id = (Integer) session.save(transientInstance);
			log.debug("persist successful");
			return id;
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void delete(UserInfo persistentInstance) {
		log.debug("removing UserInfo instance");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.delete(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public UserInfo findById(Integer id) {
		log.debug("getting UserInfo instance with id: " + id);
		try {
			Session session = sessionFactory.getCurrentSession();
			UserInfo instance = (UserInfo) session.get(UserInfo.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserInfo> getAllUserInfo() {
		log.debug("getting all UserInfo instance");
		try {

			Session session = sessionFactory.getCurrentSession();

			// Create a Hibernate query (HQL)
			Query query = session.createQuery("FROM  UserInfo ORDER BY lastname, firstname");

			// Retrieve all
			return query.list();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public void edit(UserInfo userInfo) {
		log.debug("Editing existing userInfo");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing userInfo via id
		UserInfo existingUserInfo = (UserInfo) session.get(UserInfo.class,
				userInfo.getUserId());

		// Assign updated values to this userInfo
		existingUserInfo.setFirstName(userInfo.getFirstName());
		existingUserInfo.setLastName(userInfo.getLastName());
		existingUserInfo.setInstitute(userInfo.getInstitute());
		existingUserInfo.setPhone(userInfo.getPhone());
		existingUserInfo.setMail(userInfo.getMail());
		existingUserInfo.setUsername(userInfo.getUsername());
		existingUserInfo.setRegistrationComplete(userInfo
				.getRegistrationComplete());

		// Save updates
		session.save(existingUserInfo);
	}

	@SuppressWarnings("unchecked")
	public List<UserInfo> getAllUserInfoByName(String search) {
		log.debug("getting all UserInfo instance");
		try {

			Session session = sessionFactory.getCurrentSession();

			// Create a Hibernate query (HQL)
			Query query = session.createQuery("FROM  UserInfo WHERE firstname LIKE '%"+search+"%' OR lastname LIKE '%"+search+"%' ORDER BY lastname, firstname");

			// Retrieve all
			return query.list();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	
	public UserInfo getUserInfoByUsername(String username) {
		log.debug("getting all UserInfo instance");
		try {

			Session session = sessionFactory.getCurrentSession();

			// Create a Hibernate query (HQL)
			Query query = session.createQuery("FROM  UserInfo WHERE username  = '"+username+"'");

			// Retrieve all
			return (UserInfo) query.list().get(0);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
