package brainstation.poc.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "EMPLOYEE_BANK_ACCOUNT_INFORMATION")
public class EmployeeBankAccountInformationEntity {

	private long id;
	private BankAccountTypeEntity bankAccountType;
	private String accountName;
	private String accountNumber;
	private BigDecimal currentBalance;
	private String bankName;
	private String branchName;
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

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ACCOUNT_TYPE", referencedColumnName = "CODE", nullable = false, insertable = true, updatable = true)
	public BankAccountTypeEntity getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(BankAccountTypeEntity bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	@Column(name = "ACCOUNT_NAME", nullable = false, length = 512)
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name = "ACCOUNT_NUMBER", nullable = false, length = 512, unique = true)
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Column(name = "CURRENT_BALANCE", nullable = false)
	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	@Column(name = "BANK_NAME", nullable = false, length = 512)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "BRANCH_NAME", nullable = false, length = 512)
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Column(name = "IS_DELETED", nullable = false, precision = 0)
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
