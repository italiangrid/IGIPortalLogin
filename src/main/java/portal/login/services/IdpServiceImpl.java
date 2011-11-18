package portal.login.services;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import portal.login.dao.IdpDAO;
import portal.login.domain.Idp;

@Service
public class IdpServiceImpl implements IdpService {

	private static final Logger log = Logger.getLogger(IdpServiceImpl.class);

	@Autowired
	private IdpDAO idpDAO;

	@Transactional
	public void save(Idp transientInstance) {
		log.debug("persisting Idp instance");
		idpDAO.save(transientInstance);
	}

	@Transactional
	public void delete(Idp persistentInstance) {
		log.debug("removing Idp instance");
		idpDAO.delete(persistentInstance);
	}

	@Transactional
	public Idp findById(Integer id) {
		log.debug("getting Idp instance with id: " + id);
		return idpDAO.findById(id);
	}

	@Transactional
	public String findByIdp(Idp idp) {
		log.debug("getting Idp instance");
		return idpDAO.findById(idp.getIdIdp()).getIdpname();
	}

	@Transactional
	public List<Idp> getAllIdp() {
		log.debug("getting all Idp instance");
		return idpDAO.getAllIdp();
	}
}
