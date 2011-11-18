package portal.login.dao;

import java.util.List;

import portal.login.domain.Vo;

public interface VoDAO {

	public List<Vo> getAllVo();

	public Vo findById(Integer id);

	public List<String> getAllDiscipline();

	public List<Vo> getAllVoByName(String search);

}
