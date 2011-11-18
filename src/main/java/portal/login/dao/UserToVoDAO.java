package portal.login.dao;

import java.util.List;

import portal.login.domain.UserInfo;
import portal.login.domain.UserToVo;
import portal.login.domain.Vo;

public interface UserToVoDAO {

	public void save(UserToVo transientInstance);

	public List<UserToVo> findById(UserInfo userInfo);

	public Vo getVoByUserToVo(UserToVo userToVo);

	public void delete(int userId, int idVo);

	public UserToVo findByIds(int userId, int idVo);

	public void update(UserToVo temp);

	public int getNumberOfUserToVo(int userId);

	public void deleteByIdCert(int idCert);

}
