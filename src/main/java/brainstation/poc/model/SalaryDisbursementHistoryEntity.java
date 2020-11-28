package brainstation.poc.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SALARY_DISBURSEMENT_HISTORY")
public class SalaryDisbursementHistoryEntity {
	private long id;
	private EmployeeInformationEntity employeeId;
	private BigDecimal basicSalary;
	private BigDecimal houseRent;
	private BigDecimal medicalAllowance;
	private BigDecimal grossSalary;
	private Integer year;
	private String month;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
	public EmployeeInformationEntity getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(EmployeeInformationEntity employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "BASIC_SALARY", nullable = false)
	public BigDecimal getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}

	@Column(name = "HOUSE_RENT", nullable = false)
	public BigDecimal getHouseRent() {
		return houseRent;
	}

	public void setHouseRent(BigDecimal houseRent) {
		this.houseRent = houseRent;
	}

	@Column(name = "MEDICAL_ALLOWANCE", nullable = false)
	public BigDecimal getMedicalAllowance() {
		return medicalAllowance;
	}

	public void setMedicalAllowance(BigDecimal medicalAllowance) {
		this.medicalAllowance = medicalAllowance;
	}

	@Column(name = "GROSS_SALARY", nullable = false)
	public BigDecimal getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(BigDecimal grossSalary) {
		this.grossSalary = grossSalary;
	}

	@Column(name = "YEAR", nullable = false)
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "MONTH", nullable = false)
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}
