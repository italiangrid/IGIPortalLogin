package portal.login.services;

import java.util.List;

import portal.login.domain.UserToVo;
import portal.login.domain.Vo;

public interface UserToVoService {

	public void save(UserToVo transientInstance);

	public void save(int userId, int idVo, String subject);

	public List<UserToVo> findById(int userId);

	public List<Vo> findVoByUserId(int userId);

	public void delete(int userId, int idVo);

	public boolean setDefault(int userId, int idVo);

	public UserToVo findById(int userId, int idVo);

	public void update(UserToVo utv);

	public String findDefaultVo(int userId);

	public String getDefaultFqan(int userId);

	public int getNumberOfUserToVo(int userId);

}
