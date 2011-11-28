package portal.login.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Userinfo provides information about the user stored in the database.
 * 
 * @author dmichelotto
 *
 */
@Entity
@Table(name = "userInfo", catalog = "PortalUser")
public class UserInfo implements java.io.Serializable {

	/**
	 * Serial version identifier.
	 */
	private static final long serialVersionUID = -8793989934172666454L;
	
	/**
	 * User identifier.
	 */
	private int userId;
	
	/**
	 * Idp were retrieved the information of the user.
	 */
	private Idp idp;
	
	/**
	 * First name of the user.
	 */
	private String firstName;
	
	/**
	 * Last name of the user.
	 */
	private String lastName;
	
	/**
	 * Institute of the user.
	 */
	private String institute;
	
	/**
	 * Phone number of the user.
	 */
	private String phone;
	
	/**
	 * E-mail address of the user.
	 */
	private String mail;
	
	/**
	 * Username of the user.
	 */
	private String username;
	
	/**
	 * Flag will be set true when the registration to the portal of the use will be completed.
	 */
	private String registrationComplete;
	
	/**
	 * Certificates of the user.
	 */
	private Set<Certificate> certificates = new HashSet<Certificate>(0);
	
	/**
	 * Virtual Organizations of the user.
	 */
	private Set<UserToVo> userToVos = new HashSet<UserToVo>(0);

	
	/**
	 * Default constructor of the class.
	 */
	public UserInfo() {
	}

	/**
	 * Constructor of the class using the values.
	 * @param idp: the user's idp.
	 * @param firstName: the first name of the user.
	 * @param lastName: the last name of the user.
	 * @param institute: the institute of the user.
	 * @param mail: the e-mail address of the user.
	 * @param username: the username of the user.
	 */
	public UserInfo(Idp idp, String firstName, String lastName,
			String institute, String mail, String username) {
		this.idp = idp;
		this.firstName = firstName;
		this.lastName = lastName;
		this.institute = institute;
		this.mail = mail;
		this.username = username;
	}

	/**
	 * Constructor of the class using the values.
	 * @param idp: the user's idp.
	 * @param firstName: the first name of the user.
	 * @param lastName: the last name of the user.
	 * @param institute: the institute of the user.
	 * @param mail: the e-mail address of the user.
	 * @param username: the username of the user.
	 * @param certificates: the certificates of the user.
	 * @param userToVos: the virtual organization memebership of the user. 
	 */
	public UserInfo(Idp idp, String firstName, String lastName,
			String institute, String phone, String mail, String username,
			Set<Certificate> certificates, Set<UserToVo> userToVos) {
		this.idp = idp;
		this.firstName = firstName;
		this.lastName = lastName;
		this.institute = institute;
		this.phone = phone;
		this.mail = mail;
		this.username = username;
		this.certificates = certificates;
		this.userToVos = userToVos;
	}

	/**
	 * Getter method for the user identifier.
	 * @return the identifier.
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "userId", unique = true, nullable = false)
	public int getUserId() {
		return this.userId;
	}

	/**
	 * Setter method for the user identifier.
	 * @param userId: identifiert to set.
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Getter method for the idp of the user.
	 * @return the idp of the user.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idp", nullable = false)
	public Idp getIdp() {
		return this.idp;
	}

	/**
	 * Setter method for the user identifier.
	 * @param idp: the idp to set.
	 */
	public void setIdp(Idp idp) {
		this.idp = idp;
	}

	/**
	 * Getter method for the first name of the user.
	 * @return the first name of the user.
	 */
	@Column(name = "firstName", nullable = false, length = 75)
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Setter method for the first name of the user.
	 * @param firstName: the first name  to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter method for the last name of the user.
	 * @return the last name of the user.
	 */
	@Column(name = "lastName", nullable = false, length = 75)
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Setter method for the last name of the user.
	 * @param lastName: the last name  to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter method for the institute of the user.
	 * @return the institute of the user.
	 */
	@Column(name = "institute", nullable = false, length = 75)
	public String getInstitute() {
		return this.institute;
	}

	/**
	 * Setter method for the user identifier.
	 * @param userId: identifiert to set.
	 */
	public void setInstitute(String institute) {
		this.institute = institute;
	}

	/**
	 * Getter method for the user identifier.
	 * @return the identifier-
	 */
	@Column(name = "phone", length = 75)
	public String getPhone() {
		return this.phone;
	}

	/**
	 * Setter method for the user identifier.
	 * @param userId: identifiert to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Getter method for the user identifier.
	 * @return the identifier-
	 */
	@Column(name = "mail", nullable = false, length = 75)
	public String getMail() {
		return this.mail;
	}

	/**
	 * Setter method for the user identifier.
	 * @param userId: identifiert to set.
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Getter method for the user identifier.
	 * @return the identifier-
	 */
	@Column(name = "username", nullable = false, length = 75)
	public String getUsername() {
		return this.username;
	}

	/**
	 * Setter method for the user identifier.
	 * @param userId: identifiert to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter method for the user identifier.
	 * @return the identifier-
	 */
	@Column(name = "registrationComplete", nullable = false, length = 5)
	public String getRegistrationComplete() {
		return this.registrationComplete;
	}

	/**
	 * Setter method for the user identifier.
	 * @param userId: identifiert to set.
	 */
	public void setRegistrationComplete(String registrationComplete) {
		this.registrationComplete = registrationComplete;
	}

	/**
	 * Getter method for the user identifier.
	 * @return the identifier-
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userInfo")
	public Set<Certificate> getCertificates() {
		return this.certificates;
	}

	/**
	 * Setter method for the user identifier.
	 * @param userId: identifiert to set.
	 */
	public void setCertificates(Set<Certificate> certificates) {
		this.certificates = certificates;
	}

	/**
	 * Getter method for the user identifier.
	 * @return the identifier-
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userInfo")
	public Set<UserToVo> getUserToVos() {
		return this.userToVos;
	}

	/**
	 * Setter method for the user identifier.
	 * @param userId: identifiert to set.
	 */
	public void setUserToVos(Set<UserToVo> userToVos) {
		this.userToVos = userToVos;
	}

}
