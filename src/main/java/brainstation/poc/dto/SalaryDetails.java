package brainstation.poc.dto;

import java.math.BigDecimal;

import brainstation.poc.model.EmployeeBankAccountInformationEntity;
import brainstation.poc.model.EmployeeInformationEntity;

public class SalaryDetails {

	private long employeeID;
	private String employeeName;
	private EmployeeBankAccountInformationEntity bankAccount;
	private EmployeeInformationEntity employee;

	private BigDecimal basicSalary;

	private BigDecimal houseRent;

	private BigDecimal medicalAllowance;

	private BigDecimal totalSalary;

	public SalaryDetails(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
		if (basicSalary != null) {
			this.houseRent = basicSalary.multiply(new BigDecimal(20)).divide(new BigDecimal(100));
			this.medicalAllowance = basicSalary.multiply(new BigDecimal(15)).divide(new BigDecimal(100));
			this.totalSalary = basicSalary.add(this.houseRent).add(this.medicalAllowance);
		}
	}

	public long getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(long employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public EmployeeBankAccountInformationEntity getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(EmployeeBankAccountInformationEntity bankAccount) {
		this.bankAccount = bankAccount;
	}

	public EmployeeInformationEntity getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeInformationEntity employee) {
		this.employee = employee;
	}

	public BigDecimal getBasicSalary() {
		return basicSalary;
	}

	public BigDecimal getHouseRent() {
		return houseRent;
	}

	public BigDecimal getMedicalAllowance() {
		return medicalAllowance;
	}

	public BigDecimal getTotalSalary() {
		return totalSalary;
	}

}
