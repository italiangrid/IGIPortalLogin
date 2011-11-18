package portal.login.services;

import java.util.List;

import portal.login.domain.Certificate;

public interface CertificateService {

	public Integer save(Certificate transientInstance);

	public void delete(Certificate persistentInstance);

	public int save(Certificate certificate, int userId);

	public List<Certificate> findById(int userId);

	public void delete(int idCert);

	public boolean setDefault(int idCert);

	public Certificate findBySubject(String subject);

	public Certificate findByIdCert(Certificate certificate);

	public Certificate findByIdCert(int idCert);

	public void update(Certificate cert);

}
