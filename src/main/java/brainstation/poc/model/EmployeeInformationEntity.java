package brainstation.poc.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "EMPLOYEE_INFORMATION")
public class EmployeeInformationEntity {
	private long id;
	private String name;
	private SalaryGradeEntity grade;
	private String address;
	private String mobile;
	private EmployeeBankAccountInformationEntity bankAccount;
	private Boolean isDeleted;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 512)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GRADE")
	public SalaryGradeEntity getGrade() {
		return grade;
	}

	public void setGrade(SalaryGradeEntity grade) {
		this.grade = grade;
	}

	@Column(name = "ADDRESS", nullable = false, length = 512)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "MOBILE_NUMBER", nullable = false, length = 512)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BANK_ACCOUNT", referencedColumnName = "ID")
	public EmployeeBankAccountInformationEntity getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(EmployeeBankAccountInformationEntity bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Column(name = "IS_DELETED", nullable = false, precision = 0)
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
