package brainstation.poc.dto;

import java.math.BigDecimal;
import java.util.List;

public class BalanceViewAndUpdate {

	private BigDecimal currentBalance;
	private BigDecimal addedBalanceAmount;
	private List<SalaryDetails> salaryList;

	private boolean incompleteDisbursement;
	private String month;
	private int year;

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public BigDecimal getAddedBalanceAmount() {
		return addedBalanceAmount;
	}

	public void setAddedBalanceAmount(BigDecimal addedBalanceAmount) {
		this.addedBalanceAmount = addedBalanceAmount;
	}

	public List<SalaryDetails> getSalaryList() {
		return salaryList;
	}

	public void setSalaryList(List<SalaryDetails> salaryList) {
		this.salaryList = salaryList;
	}

	public boolean isIncompleteDisbursement() {
		return incompleteDisbursement;
	}

	public void setIncompleteDisbursement(boolean incompleteDisbursement) {
		this.incompleteDisbursement = incompleteDisbursement;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
