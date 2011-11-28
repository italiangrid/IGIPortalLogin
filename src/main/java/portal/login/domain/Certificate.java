package portal.login.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Certificate provides information about the certificate stored in the database.
 * 
 * 
 * @author dmichelotto
 * 
 */
@Entity
@Table(name = "certificate", catalog = "PortalUser")
public class Certificate implements java.io.Serializable {

	/**
	 * Serial Version Identifier.
	 */
	private static final long serialVersionUID = -4219251531396625106L;
	
	/**
	 * Certificate identifier.
	 */
	private int idCert;
	
	/**
	 * User who owns the certificate.
	 */
	private UserInfo userInfo;
	
	/**
	 * Subject of the certificate.
	 */
	private String subject;
	
	/**
	 * Expiration date of the certificate.
	 */
	private Date expirationDate;
	
	/**
	 * Flag that setted true if the certificate was released by the ca-online.
	 */
	private String caonline;
	
	/**
	 * Flag that setted true if the user set this certificate such as default.
	 */
	private String primaryCert;
	
	/**
	 * Issuer of the certificate.
	 */
	private String issuer;
	
	/**
	 * Username used for store proxy certificate in the myproxy repository.
	 * This username was compose by [username of the owner]_[certificate identifier].
	 */
	private String usernameCert;

	/**
	 * Default constructor of the class.
	 */
	public Certificate() {
	}

	/**
	 * Constructor of the class using the values.
	 * @param userInfo: owner of the certificate.
	 * @param subject: subject of the certificate.
	 * @param expirationDate: expiration date of the certificate.
	 * @param caonline: true if released by ca-online.
	 * @param primaryCert: true if is the certificate of default.
	 * @param issuer: issuer of the certificate.
	 * @param usernameCert: username of the certificate for myproxy repository.
	 */
	public Certificate(UserInfo userInfo, String subject, Date expirationDate,
			String caonline, String primaryCert, String issuer,
			String usernameCert) {
		this.userInfo = userInfo;
		this.subject = subject;
		this.expirationDate = expirationDate;
		this.caonline = caonline;
		this.primaryCert = primaryCert;
		this.issuer = issuer;
		this.usernameCert = usernameCert;
	}

	/**
	 * Getter method for certificate identifier.
	 * @return the certificate identifier.
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idCert", unique = true, nullable = false)
	public int getIdCert() {
		return this.idCert;
	}

	/**
	 * Setter method for certificate identifier.
	 * @param idCert: the certificate identifier to set.
	 */
	public void setIdCert(int idCert) {
		this.idCert = idCert;
	}

	/**
	 * Getter method for retrive the owner of the certificate.
	 * @return the own of the certificate.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	/**
	 * Setter method for the owner of the certificate.
	 * @param userInfo: the owner to set
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * Getter method for the subject of the certificate.
	 * @return the subject of the certificate.
	 */
	@Column(name = "subject", nullable = false, length = 100)
	public String getSubject() {
		return this.subject;
	}

	/**
	 * Setter method for the subject of the certificate.
	 * @param subject: the subject of the certificate to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Getter method for the expiration date of the certificate.
	 * @return the expiration date of the certificate.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "expirationDate", nullable = false, length = 10)
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	/**
	 * Setter method for the expirationDate of the certificate.
	 * @param expirationDate: the expiration date of the certificate to set.
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * Getter method for the caonline of the certificate.
	 * @return the boolena value of flag.
	 */
	@Column(name = "CAonline", nullable = false, length = 6)
	public String getCaonline() {
		return this.caonline;
	}

	/**
	 * Setter method for the caonline of the certificate.
	 * @param caonline: the boolean value to set.
	 */
	public void setCaonline(String caonline) {
		this.caonline = caonline;
	}

	/**
	 * Getter method for the primaryCert of the certificate.
	 * @return the boolean value of flag.
	 */
	@Column(name = "primaryCert", nullable = false, length = 6)
	public String getPrimaryCert() {
		return this.primaryCert;
	}

	/**
	 * Setter method for the primaryCert of the certificate.
	 * @param primaryCert: the boolean value to set.
	 */
	public void setPrimaryCert(String primaryCert) {
		this.primaryCert = primaryCert;
	}

	/**
	 * Getter method for the issuer of the certificate.
	 * @return the issuer of the certificate.
	 */
	@Column(name = "issuer", nullable = false, length = 100)
	public String getIssuer() {
		return this.issuer;
	}

	/**
	 * Setter method for the issuer of the certificate.
	 * @param subject: the issuer of the certificate to set.
	 */
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	/**
	 * Getter method for the username of the certificate.
	 * @return the username of the certificate.
	 */
	@Column(name = "usernameCert", nullable = false, length = 100)
	public String getUsernameCert() {
		return this.usernameCert;
	}

	/**
	 * Setter method for the username of the certificate.
	 * @param usernameCert: the username of the certificate to set.
	 */
	public void setUsernameCert(String usernameCert) {
		this.usernameCert = usernameCert;
	}

	/**
	 * Check if two certificate are equal
	 * @param cert: the certificate to evaluate
	 * @return true if the two certificare have the same issuer and subject
	 */
	public boolean equals(Certificate cert) {
		if (this.subject.equals(cert.subject)
				&& this.issuer.equals(cert.issuer))
			return true;
		return false;
	}

}
