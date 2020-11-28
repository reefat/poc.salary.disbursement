package brainstation.poc.dto;

import java.math.BigDecimal;

public class GradeBasicPay {
	private int grade;
	private BigDecimal basicSalary;

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public BigDecimal getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}

}
