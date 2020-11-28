package brainstation.poc.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BANK_ACCOUNT_TYPE")
public class BankAccountTypeEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3766004118465780126L;
	
	private long id;
	private String code;
	private String name;
	private Set<EmployeeBankAccountInformationEntity> bankInfo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "CODE", nullable = false, length = 5, unique = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME", nullable = false, length = 10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bankAccountType")
	public Set<EmployeeBankAccountInformationEntity> getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(Set<EmployeeBankAccountInformationEntity> bankInfo) {
		this.bankInfo = bankInfo;
	}

}
