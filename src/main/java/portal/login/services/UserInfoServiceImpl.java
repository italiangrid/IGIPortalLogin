package portal.login.services;

import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import portal.login.dao.IdpDAO;
import portal.login.dao.UserInfoDAO;
import portal.login.domain.UserInfo;
import portal.login.domain.Idp;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	private static final Logger log = Logger
			.getLogger(UserInfoServiceImpl.class);

	@Autowired
	private UserInfoDAO userInfoDAO;

	@Autowired
	private IdpDAO idpDAO;

	@Transactional
	public Integer save(UserInfo transientInstance) {
		log.debug("persisting UserInfo instance");
		return userInfoDAO.save(transientInstance);
	}

	@Transactional
	public void delete(UserInfo persistentInstance) {
		log.debug("removing UserInfo instance");
		userInfoDAO.delete(persistentInstance);
	}

	@Transactional
	public void delete(int userId) {
		log.debug("removing UserInfo instance");
		UserInfo persistentInstance = userInfoDAO.findById(userId);
		userInfoDAO.delete(persistentInstance);
	}

	@Transactional
	public UserInfo findById(Integer id) {
		log.debug("getting UserInfo instance with id: " + id);
		return userInfoDAO.findById(id);
	}

	@Transactional
	public List<UserInfo> getAllUserInfo() {
		log.debug("getting all UserInfo instance");
		return userInfoDAO.getAllUserInfo();
	}

	@Transactional
	public void edit(UserInfo userInfo) {
		log.debug("Editing existing userInfo");
		userInfoDAO.edit(userInfo);
	}

	@Transactional
	public int save(UserInfo userInfo, int idIDP) {
		log.debug("persisting UserInfo instance");
		Idp idp = idpDAO.findById(idIDP);
		userInfo.setIdp(idp);
		return userInfoDAO.save(userInfo);
	}

	@Transactional
	public List<UserInfo> getAllUserInfoByName(String search) {
		log.debug("getting all UserInfo instance");
		return userInfoDAO.getAllUserInfoByName(search);
	}
	
	@Transactional
	public UserInfo findByUsername(String username) {
		log.debug("getting all UserInfo instance");
		return userInfoDAO.getUserInfoByUsername(username);
	}

}
