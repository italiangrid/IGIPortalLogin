package portal.login.services;

import java.util.List;

import portal.login.domain.Vo;

public interface VoService {

	public List<Vo> getAllVo();

	public Vo findById(Integer id);

	public String findByVo(Vo vo);

	public List<String> getAllDiscplines();

	public List<Vo> getAllVoByName(String search);

}
