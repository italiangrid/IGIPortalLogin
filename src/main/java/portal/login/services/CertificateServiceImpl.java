package portal.login.services;

// Generated 1-giu-2011 15.47.08 by Hibernate Tools 3.4.0.CR1

//import javax.ejb.Stateless;
//import javax.persistence.sessionFactory;
//import javax.persistence.PersistenceContext;
import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import portal.login.dao.CertificateDAO;
import portal.login.dao.UserInfoDAO;
import portal.login.dao.UserToVoDAO;
import portal.login.domain.Certificate;
import portal.login.domain.UserInfo;

/**
 * Home object for domain model class UserInfo.
 * 
 * @see portal.login.services.UserInfo
 * @author Hibernate Tools
 */
@Service
public class CertificateServiceImpl implements CertificateService {

	private static final Logger log = Logger
			.getLogger(CertificateServiceImpl.class);

	@Autowired
	private CertificateDAO certificateDAO;
	
	@Autowired
	private UserToVoDAO userToVoDAO;

	@Autowired
	private UserInfoDAO userInfoDAO;

	@Transactional
	public Integer save(Certificate transientInstance) {
		log.debug("persisting Certificate instance");
		return certificateDAO.save(transientInstance);
	}

	@Transactional
	public void delete(Certificate persistentInstance) {
		log.debug("removing Certificate instance");
		certificateDAO.delete(persistentInstance);
	}

	@Transactional
	public void delete(int idCert) {
		log.debug("removing Certificate instance");
		
		userToVoDAO.deleteByIdCert(idCert);
		Certificate persistentInstance = certificateDAO.findById(idCert);
		
		certificateDAO.delete(persistentInstance);
	}

	@Transactional
	public int save(Certificate certificate, int userId) {
		log.debug("persisting Certificate instance");
		UserInfo userInfo = userInfoDAO.findById(userId);
		List<Certificate> lc = certificateDAO.findById(userInfo);

		certificate.setUserInfo(userInfo);
		certificate.setUsernameCert(userInfo.getUsername() + "_"
				+ certificate.getIdCert());

		if (certificate.getPrimaryCert().equals("true")) {

			Certificate tmp = null;

			int i = 0;

			for (i = 0; i < lc.size(); i++) {
				tmp = lc.get(i);
				tmp.setPrimaryCert("false");
				certificateDAO.edit(tmp);
			}
		}

		int result = certificateDAO.save(certificate);

		certificate.setUsernameCert(userInfo.getUsername() + "_" + result);

		certificateDAO.edit(certificate);

		return result;
	}

	@Transactional
	public List<Certificate> findById(int userId) {
		UserInfo userInfo = userInfoDAO.findById(userId);
		return certificateDAO.findById(userInfo);
	}

	@Transactional
	public boolean setDefault(int idCert) {
		log.debug("ugrading Certificate instance");
		Certificate persistentInstance = certificateDAO.findById(idCert);

		if (persistentInstance.getPrimaryCert().equals("false")) {
			List<Certificate> lc = certificateDAO.findById(persistentInstance
					.getUserInfo());
			Certificate tmp = null;

			int i = 0;

			for (i = 0; i < lc.size(); i++) {
				tmp = lc.get(i);
				tmp.setPrimaryCert("false");
				certificateDAO.edit(tmp);
			}

			persistentInstance.setPrimaryCert("true");
			certificateDAO.edit(persistentInstance);
			return true;
		}

		return false;
	}

	@Transactional
	public Certificate findBySubject(String subject) {
		return certificateDAO.findBySubject(subject);
	}

	@Transactional
	public Certificate findByIdCert(Certificate certificate) {
		return certificateDAO.findById(certificate.getIdCert());
	}

	@Transactional
	public Certificate findByIdCert(int idCert) {
		return certificateDAO.findById(idCert);
	}

	@Transactional
	public void update(Certificate cert) {
		certificateDAO.update(cert);
	}

}
