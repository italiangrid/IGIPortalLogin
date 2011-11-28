package portal.login.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Idp rovides information about the idps supported by the portal
 * 
 * @author dmichelotto
 *
 */
@Entity
@Table(name = "IDP", catalog = "PortalUser")
public class Idp implements java.io.Serializable {

	/**
	 * Serial Version Identifier.
	 */
	private static final long serialVersionUID = -2795578006748945701L;
	
	/**
	 * Idp identifier.
	 */
	private int idIdp;
	
	/**
	 * The name of the Idp.
	 */
	private String idpname;
	
	/**
	 * The hostname of the idp.
	 */
	private String idpaddress;
	
	/**
	 * The address for authentication via casshib.
	 */
	private String idploginAddress;
	
	/**
	 * The address for request the parameter released by the idp.
	 */
	private String idpparameterRequest;
	
	/**
	 * Not used.
	 * @deprecated
	 */
	private String idpattributeRequest;
	
	/**
	 * Description of the idp.
	 */
	private String idpdescription;

	/**
	 * Default constructor.
	 */
	public Idp() {
	}

	/**
	 * Constructor of the class usign the values.
	 * @param idpname: name of the idp.
	 * @param idpaddress: hostname of the idp.
	 * @param idploginAddress: address for the login.
	 * @param idpparameterRequest: address for request parameters released from the idp.
	 * @param idpattributeRequest: not used.
	 * @param idpdescription: description of the idp.
	 */
	public Idp(String idpname, String idpaddress, String idploginAddress,
			String idpparameterRequest, String idpattributeRequest,
			String idpdescription) {
		this.idpname = idpname;
		this.idpaddress = idpaddress;
		this.idploginAddress = idploginAddress;
		this.idpparameterRequest = idpparameterRequest;
		this.idpattributeRequest = idpattributeRequest;
		this.idpdescription = idpdescription;
	}

	/**
	 * Getter method for the idp identifier.
	 * @return the identifier of the idp.
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idIDP", unique = true, nullable = false)
	public int getIdIdp() {
		return this.idIdp;
	}

	/**
	 * Setter method for the identifier.
	 * @param idIdp: the value to set.
	 */
	public void setIdIdp(int idIdp) {
		this.idIdp = idIdp;
	}

	/**
	 * Getter method for the idp identifier.
	 * @return the identifier of the idp.
	 */
	@Column(name = "IDPName", nullable = false, length = 75)
	public String getIdpname() {
		return this.idpname;
	}

	/**
	 * Setter method for the identifier.
	 * @param idIdp: the value to set.
	 */
	public void setIdpname(String idpname) {
		this.idpname = idpname;
	}

	/**
	 * Getter method for the idp identifier.
	 * @return the identifier of the idp.
	 */
	@Column(name = "IDPAddress", nullable = false, length = 100)
	public String getIdpaddress() {
		return this.idpaddress;
	}

	/**
	 * Setter method for the identifier.
	 * @param idIdp: the value to set.
	 */
	public void setIdpaddress(String idpaddress) {
		this.idpaddress = idpaddress;
	}

	/**
	 * Getter method for the idp identifier.
	 * @return the identifier of the idp.
	 */
	@Column(name = "IDPLoginAddress", nullable = false, length = 200)
	public String getIdploginAddress() {
		return this.idploginAddress;
	}

	/**
	 * Setter method for the identifier.
	 * @param idIdp: the value to set.
	 */
	public void setIdploginAddress(String idploginAddress) {
		this.idploginAddress = idploginAddress;
	}

	/**
	 * Getter method for the idp identifier.
	 * @return the identifier of the idp.
	 */
	@Column(name = "IDPParameterRequest", nullable = false, length = 200)
	public String getIdpparameterRequest() {
		return this.idpparameterRequest;
	}

	/**
	 * Setter method for the identifier.
	 * @param idIdp: the value to set.
	 */
	public void setIdpparameterRequest(String idpparameterRequest) {
		this.idpparameterRequest = idpparameterRequest;
	}

	/**
	 * Getter method for the idp identifier.
	 * @return the identifier of the idp.
	 */
	@Column(name = "IDPAttributeRequest", nullable = false, length = 200)
	public String getIdpattributeRequest() {
		return this.idpattributeRequest;
	}

	/**
	 * Setter method for the identifier.
	 * @param idIdp: the value to set.
	 */
	public void setIdpattributeRequest(String idpattributeRequest) {
		this.idpattributeRequest = idpattributeRequest;
	}

	/**
	 * Getter method for the idp identifier.
	 * @return the identifier of the idp.
	 */
	@Column(name = "IDPDescription", nullable = false, length = 65535)
	public String getIdpdescription() {
		return this.idpdescription;
	}

	/**
	 * Setter method for the identifier.
	 * @param idIdp: the value to set.
	 */
	public void setIdpdescription(String idpdescription) {
		this.idpdescription = idpdescription;
	}

}
