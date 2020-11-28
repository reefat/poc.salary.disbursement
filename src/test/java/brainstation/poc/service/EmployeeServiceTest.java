package brainstation.poc.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import brainstation.poc.dto.Employee;
import brainstation.poc.model.EmployeeInformationEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {
	
	@Autowired
	private EmployeeService employeeService;

	@Test
	public void addEmployee() throws Exception {
		Employee employee = new Employee();

		employee.setAccountName("Anwar Reefat");
		employee.setAccountNumber("4125896325874");
		employee.setAddress("farmgate, dhaka");
		employee.setBankAccountTypeCode("SB");
		employee.setBankName("Mutual Trust Bank");
		employee.setBranchName("Panthapath");
		employee.setGrade(3);
		employee.setMobile("01610456654");
		employee.setName("Anwar Reefat");

		employeeService.addEmployee(employee);
	}

	@Test
	public void fetchAllEmployees() throws Exception {
		addEmployee();
		
		List<EmployeeInformationEntity> list = employeeService.viewList();
		Assert.assertTrue("List empty", list.size() > 0);
	}
}
