package portal.login.services;

// Generated 1-giu-2011 15.47.08 by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import portal.login.dao.CertificateDAO;
import portal.login.dao.UserInfoDAO;
import portal.login.dao.UserToVoDAO;
import portal.login.dao.VoDAO;
import portal.login.domain.Certificate;
import portal.login.domain.UserInfo;
import portal.login.domain.UserToVo;
import portal.login.domain.UserToVoId;
import portal.login.domain.Vo;

/**
 * Home object for domain model class UserToVo.
 * 
 * @see portal.login.services.UserToVo
 * @author Hibernate Tools
 */
@Service
public class UserToVoServiceImpl implements UserToVoService {

	private static final Logger log = Logger
			.getLogger(UserInfoServiceImpl.class);

	@Autowired
	private UserToVoDAO userToVoDAO;
	
	@Autowired
	private CertificateDAO certificateDAO;

	@Autowired
	private UserInfoDAO userInfoDAO;

	@Autowired
	private VoDAO voDAO;

	public UserToVoServiceImpl() {

	}

	@Transactional
	public void save(UserToVo transientInstance) {
		log.debug("persisting UserToVoService instance");
		userToVoDAO.save(transientInstance);
	}

	@Transactional
	public void save(int userId, int idVo, String subject) {
		log.debug("persisting UserToVoService instance");

		UserToVo transientInstance = new UserToVo();

		UserToVoId utvid = new UserToVoId(userId, idVo);

		transientInstance.setId(utvid);

		UserInfo userInfo = userInfoDAO.findById(userId);
		Vo vo = voDAO.findById(idVo);
		
		
		Certificate certificate = null;
		List<Certificate> certs = certificateDAO.findById(userInfo);
		for (Iterator<Certificate> iterator = certs.iterator(); iterator
				.hasNext();) {
			certificate = (Certificate) iterator.next();
			if(certificate.getSubject().equals(subject)){
				break;
			}
			
		}
		
		log.info("subject = " + subject + " certificate.subject" + certificate.getSubject());

		transientInstance.setUserInfo(userInfo);
		transientInstance.setVo(vo);
		transientInstance.setCertificate(certificate);

		transientInstance.setIsDefault("false");

		userToVoDAO.save(transientInstance);

	}

	@Transactional
	public List<UserToVo> findById(int userId) {
		UserInfo userInfo = userInfoDAO.findById(userId);
		return userToVoDAO.findById(userInfo);
	}

	@Transactional
	public List<Vo> findVoByUserId(int userId) {

		UserInfo userInfo = userInfoDAO.findById(userId);
		List<UserToVo> utvs = userToVoDAO.findById(userInfo);

		List<Vo> vos = new ArrayList<Vo>();

		for (int i = 0; i < utvs.size(); i++) {

			vos.add(userToVoDAO.getVoByUserToVo(utvs.get(i)));

		}

		return vos;
	}

	@Transactional
	public void delete(int userId, int idVo) {

		userToVoDAO.delete(userId, idVo);

	}

	@Transactional
	public boolean setDefault(int userId, int idVo) {

		UserToVo toUpdate = userToVoDAO.findByIds(userId, idVo);

		if (!toUpdate.getIsDefault().equals("true")) {

			UserInfo userInfo = userInfoDAO.findById(userId);

			List<UserToVo> utvs = userToVoDAO.findById(userInfo);

			UserToVo temp = null;

			for (int i = 0; i < utvs.size(); i++) {
				temp = utvs.get(i);
				temp.setIsDefault("false");
				userToVoDAO.update(temp);
			}

			toUpdate.setIsDefault("true");
			userToVoDAO.update(temp);

			return true;
		}
		return false;
	}

	@Transactional
	public UserToVo findById(int userId, int idVo) {
		return userToVoDAO.findByIds(userId, idVo);
	}

	@Transactional
	public void update(UserToVo utv) {
		userToVoDAO.update(utv);

	}

	@Transactional
	public String findDefaultVo(int userId) {
		List<UserToVo> utvs = findById(userId);
		UserToVoId utvId = null;
		Vo vo = null;
		String voName = null;
		for (int i = 0; i < utvs.size(); i++) {
			if (utvs.get(i).getIsDefault().equals("true")) {
				utvId = utvs.get(i).getId();
				vo = voDAO.findById(utvId.getIdVo());
				voName = vo.getVo();
				break;
			}
		}
		return voName;
	}

	@Transactional
	public String getDefaultFqan(int userId) {
		List<UserToVo> utvs = findById(userId);
		String fqan = null;
		for (int i = 0; i < utvs.size(); i++) {
			if (utvs.get(i).getIsDefault().equals("true")) {

				fqan = utvs.get(i).getFqans();
				break;
			}
		}
		return fqan;
	}

	@Transactional
	public int getNumberOfUserToVo(int userId) {

		return userToVoDAO.getNumberOfUserToVo(userId);
	}

}
