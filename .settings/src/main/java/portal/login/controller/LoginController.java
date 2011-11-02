package portal.login.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.portlet.RenderResponse;

import portal.login.domain.Idp;
import portal.login.dao.IdpDAO;



import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;





@Controller("userInfoController")
@RequestMapping(value = "VIEW")
public class LoginController {
	

	@Autowired
	@Qualifier("myIdpDAO")
	@Resource(name="idpDAO")
	private IdpDAO idpDAO;

	
	public void setIdpDAO(IdpDAO idpDAO) {
		this.idpDAO = idpDAO;
	}
	
	@RenderMapping
	public String showLogin(RenderResponse response) {
		return "home";
	}
	
	
	@ModelAttribute("idps")
	public List<Idp> getIdps() {
		return idpDAO.getAllIdp();
	}
	

}
