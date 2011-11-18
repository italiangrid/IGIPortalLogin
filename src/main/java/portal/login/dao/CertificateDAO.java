package portal.login.dao;

import java.util.List;

import portal.login.domain.Certificate;
import portal.login.domain.UserInfo;

public interface CertificateDAO {

	public Integer save(Certificate transientInstance);

	public void delete(Certificate persistentInstance);

	public List<Certificate> findById(UserInfo userInfo);

	public Certificate findById(Integer id);

	public void edit(Certificate certificate);

	public Certificate findBySubject(String subject);

	public void update(Certificate cert);

}
