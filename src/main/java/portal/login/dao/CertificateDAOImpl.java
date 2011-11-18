package portal.login.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import portal.login.domain.Certificate;
import portal.login.domain.UserInfo;

@Repository
public class CertificateDAOImpl implements CertificateDAO {

	private static final Logger log = Logger
			.getLogger(CertificateDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Integer save(Certificate transientInstance) {
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

	public void delete(Certificate persistentInstance) {
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

	public Certificate findById(Integer id) {
		log.debug("getting Certificate instance with id: " + id);
		try {
			Session session = sessionFactory.getCurrentSession();
			Certificate instance = (Certificate) session.get(Certificate.class,
					id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Certificate> findById(UserInfo userInfo) {
		log.debug("getting all Certificate instance of user");
		try {

			Session session = sessionFactory.getCurrentSession();

			// Create a Hibernate query (HQL)
			Query query = session
					.createQuery("FROM  Certificate WHERE userId = "
							+ userInfo.getUserId());

			// Retrieve all
			return query.list();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public void edit(Certificate certificate) {
		log.debug("Editing existing certificate");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing userInfo via id
		Certificate existingCertificate = (Certificate) session.get(
				Certificate.class, certificate.getIdCert());

		// Assign updated values to this userInfo
		existingCertificate.setCaonline(certificate.getCaonline());
		existingCertificate.setExpirationDate(certificate.getExpirationDate());
		existingCertificate.setIssuer(certificate.getIssuer());
		existingCertificate.setPrimaryCert(certificate.getPrimaryCert());
		existingCertificate.setSubject(certificate.getSubject());
		existingCertificate.setUserInfo(certificate.getUserInfo());
		existingCertificate.setUsernameCert(certificate.getUsernameCert());

		// Save updates
		session.save(existingCertificate);

	}

	public Certificate findBySubject(String subject) {
		log.debug("getting all Certificate instance of user");
		try {

			Session session = sessionFactory.getCurrentSession();

			// Create a Hibernate query (HQL)
			
			Query query = session.createQuery("FROM  Certificate WHERE subject = '"+ subject +"'");
			return (Certificate) query.list().get(0);

		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public void update(Certificate cert) {
		log.debug("Editing existing userInfo");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing userInfo via id
		Certificate existingCertificate = (Certificate) session.get(Certificate.class,
				cert.getIdCert());

		// Assign updated values to this userInfo
		existingCertificate.setSubject(cert.getSubject());
		existingCertificate.setIssuer(cert.getIssuer());
		existingCertificate.setExpirationDate(cert.getExpirationDate());
		existingCertificate.setCaonline(cert.getCaonline());
		existingCertificate.setPrimaryCert(cert.getPrimaryCert());
		existingCertificate.setUsernameCert(cert.getUsernameCert());
		existingCertificate.setUserInfo(cert.getUserInfo());

		// Save updates
		session.save(existingCertificate);
	}

}
