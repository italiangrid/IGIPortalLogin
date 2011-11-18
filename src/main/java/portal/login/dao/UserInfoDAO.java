package portal.login.dao;

import java.util.List;

import portal.login.domain.UserInfo;

public interface UserInfoDAO {

	public Integer save(UserInfo transientInstance);

	public void delete(UserInfo persistentInstance);

	public UserInfo findById(Integer id);

	public List<UserInfo> getAllUserInfo();

	public void edit(UserInfo userInfo);

	public List<UserInfo> getAllUserInfoByName(String search);
	
	public UserInfo getUserInfoByUsername(String username);

}
