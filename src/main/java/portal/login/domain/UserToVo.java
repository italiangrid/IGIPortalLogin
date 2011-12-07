package portal.login.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UserToVo provides information about the association user and VO stored in the database.
 * 
 * @author dmichelotto
 *
 */
@Entity
@Table(name = "userToVO", catalog = "PortalUser")
public class UserToVo implements java.io.Serializable {

	/**
	 * Serial version identifier.
	 */
	private static final long serialVersionUID = -5434829959406617889L;
	
	/**
	 * The UserToVo identfier.
	 */
	private UserToVoId id;
	
	/**
	 * The VO of the association.
	 */
	private Vo vo;
	
	/**
	 * The UserInfo of the association.
	 */
	private UserInfo userInfo;
	
	/**
	 * Flag that identify if the association is of default.
	 */
	private String isDefault;
	/**
	 * String that contains all the FQANs of the association divided by semi colon.
	 */
	private String fqans;
	/**
	 * The certificate used for the association.
	 */
	private Certificate certificate;

	/**
	 * Default constructor.
	 */
	public UserToVo() {
	}
	
	/**
	 * Constructor with parameter.
	 * @param id: unique identifier of the association.
	 * @param vo: VO of the association.
	 * @param userInfo: User of the association.
	 */
	public UserToVo(UserToVoId id, Vo vo, UserInfo userInfo) {
		this.id = id;
		this.vo = vo;
		this.userInfo = userInfo;
	}

	/**
	 * Constructor with parameter.
	 * @param id: unique identifier of the association.
	 * @param vo: VO of the association.
	 * @param userInfo: User of the association.
	 * @param isDefault: 
	 * @param fqans
	 * @param certificate
	 */
	public UserToVo(UserToVoId id, Vo vo, UserInfo userInfo, String isDefault,
			String fqans, Certificate certificate) {
		this.id = id;
		this.vo = vo;
		this.userInfo = userInfo;
		this.isDefault = isDefault;
		this.fqans = fqans;
		this.certificate = certificate;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "userId", nullable = false)),
			@AttributeOverride(name = "idVo", column = @Column(name = "idVO", nullable = false)) })
	public UserToVoId getId() {
		return this.id;
	}

	public void setId(UserToVoId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idVO", nullable = false, insertable = false, updatable = false)
	public Vo getVo() {
		return this.vo;
	}

	public void setVo(Vo vo) {
		this.vo = vo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false)
	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Column(name = "isDefault", length = 5)
	public String getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	@Column(name = "FQANs", length = 65535)
	public String getFqans() {
		return this.fqans;
	}

	public void setFqans(String fqans) {
		this.fqans = fqans;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCert", nullable = false)
	public Certificate getCertificate(){
		return this.certificate;
	}
	
	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}
}
