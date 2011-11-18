package portal.login.services;

import java.util.List;

import portal.login.domain.Idp;

public interface IdpService {

	public void save(Idp transientInstance);

	public void delete(Idp persistentInstance);

	public Idp findById(Integer id);

	public String findByIdp(Idp idp);

	public List<Idp> getAllIdp();

}
