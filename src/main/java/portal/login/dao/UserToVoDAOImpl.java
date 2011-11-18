package portal.login.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import portal.login.domain.UserInfo;
import portal.login.domain.UserToVo;
import portal.login.domain.UserToVoId;
import portal.login.domain.Vo;

@Repository
public class UserToVoDAOImpl implements UserToVoDAO {

	private static final Logger log = Logger.getLogger(UserToVoDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void save(UserToVo transientInstance) {
		log.debug("persisting UserInfo instance");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(transientInstance);
			//
			session.flush();
			session.refresh(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserToVo> findById(UserInfo userInfo) {
		log.debug("getting all Certificate instance of user");
		try {

			Session session = sessionFactory.getCurrentSession();

			// Create a Hibernate query (HQL)
			Query query = session.createQuery("FROM  UserToVo WHERE userId = "
					+ userInfo.getUserId());

			// Retrieve all
			return query.list();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public Vo getVoByUserToVo(UserToVo userToVo) {
		log.debug("getting all Certificate instance of user");
		try {

			Session session = sessionFactory.getCurrentSession();

			return (Vo) session.get(Vo.class,
					session.getIdentifier(userToVo.getVo()));
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public void delete(int userId, int idVo) {
		log.debug("getting all Certificate instance of user");
		try {

			Session session = sessionFactory.getCurrentSession();

			// Create a Hibernate query (HQL)
			UserToVo utv = findByIds(userId, idVo);
			session.delete(utv);
			session.flush();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}

	}

	public UserToVo findByIds(int userId, int idVo) {
		log.debug("getting all Certificate instance of user");
		try {

			Session session = sessionFactory.getCurrentSession();

			UserToVoId userToVoId = new UserToVoId(userId, idVo);

			// Create a Hibernate query (HQL)
			return (UserToVo) session.get(UserToVo.class, userToVoId);

		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;

		}
	}

	public void update(UserToVo temp) {
		log.debug("Editing existing userToVo");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing userInfo via id
		UserToVo existingUTV = (UserToVo) session.get(UserToVo.class,
				temp.getId());

		// Assign updated values to this userInfo
		existingUTV.setFqans(temp.getFqans());
		existingUTV.setIsDefault(temp.getIsDefault());
		existingUTV.setUserInfo(temp.getUserInfo());
		existingUTV.setVo(temp.getVo());
		existingUTV.setCertificate(temp.getCertificate());

		// Save updates
		session.save(existingUTV);
		session.flush();
		session.refresh(existingUTV);
	}

	@SuppressWarnings("rawtypes")
	public int getNumberOfUserToVo(int userId) {
		log.debug("getting the number of Vo fo userId = " + userId);
		try {

			Session session = sessionFactory.getCurrentSession();
			int count = 0;
			String QUERY = "FROM UserToVo WHERE userId = "
					+ userId;
			Query query = session.createQuery(QUERY);
			for (Iterator it = query.iterate(); it.hasNext();) {
				it.next();
				count++;
			}

			return count;

		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;

		}
	}

	public void deleteByIdCert(int idCert) {
		log.debug("deleting istances with idCert = " + idCert);
		try {

			Session session = sessionFactory.getCurrentSession();
		
			Query query = session.createQuery("FROM UserToVo WHERE idCert = " + idCert);
			session.flush();
			
			@SuppressWarnings("unchecked")
			List<UserToVo> utvs = query.list();
			
			for (Iterator<UserToVo> iterator = utvs.iterator(); iterator.hasNext();) {
				UserToVo userToVo = (UserToVo) iterator.next();
				session.delete(userToVo);
			}
			

		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;

		}
		
	}

}
