package brainstation.poc.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "SALARY_GRADE")
public class SalaryGradeEntity {
	private long id;
	private int grade;
	private BigDecimal basicSalary;
	
	private Set<EmployeeInformationEntity> employeeList;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Range(min = 1, max = 6)
	@Column(name = "GRADE", nullable = false)
	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "grade")
	public Set<EmployeeInformationEntity> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(Set<EmployeeInformationEntity> employeeList) {
		this.employeeList = employeeList;
	}

	@Column(name = "BASIC_SALARY", nullable = true)
	public BigDecimal getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}

}
