package portal.login.dao;

import java.util.List;

import portal.login.domain.Idp;

public interface IdpDAO {

	public void save(Idp transientInstance);

	public void delete(Idp persistentInstance);

	public Idp findById(Integer id);

	public List<Idp> getAllIdp();
}
