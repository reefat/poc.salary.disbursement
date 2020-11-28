package brainstation.poc.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstation.poc.dto.Employee;
import brainstation.poc.dto.SalaryDetails;
import brainstation.poc.model.BankAccountTypeEntity;
import brainstation.poc.model.EmployeeBankAccountInformationEntity;
import brainstation.poc.model.EmployeeInformationEntity;
import brainstation.poc.model.SalaryGradeEntity;
import brainstation.poc.repository.BankAccountTypeRepository;
import brainstation.poc.repository.EmployeeBankAccountInformationRepository;
import brainstation.poc.repository.EmployeeInformationRepository;
import brainstation.poc.repository.SalaryGradeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeInformationRepository employeeInformationRepository;

	@Autowired
	private SalaryGradeRepository salaryGradeRepository;

	@Autowired
	private EmployeeBankAccountInformationRepository employeeBankAccountInformationRepository;

	@Autowired
	private BankAccountTypeRepository bankAccountTypeRepository;

	@Transactional(rollbackFor = { Exception.class })
	public void upload(File xlFile) throws Exception {
		try {
			FileInputStream excelFile = new FileInputStream(xlFile.getAbsolutePath());
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = datatypeSheet.iterator();
			boolean isFirstRow = true;

			while (rowIterator.hasNext()) {
				Row currentRow = rowIterator.next();
				if (!isFirstRow) {
					Iterator<Cell> cellIterator = currentRow.iterator();
					Employee employeeReq = generateEmployee(cellIterator);
					if (employeeReq != null) {
						addEmployee(employeeReq);
					}
				} else {
					isFirstRow = false;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional(rollbackFor = { Exception.class })
	public void addEmployee(Employee employeeReq) throws Exception {
		EmployeeInformationEntity employeeEntity = new EmployeeInformationEntity();

		Optional<SalaryGradeEntity> optGrade = salaryGradeRepository.findByGrade(employeeReq.getGrade());
		if (!optGrade.isPresent()) {
			throw new Exception("Invalid Salary Grade " + employeeReq.getGrade());
		}

		SalaryGradeEntity grade = optGrade.get();

		Optional<BankAccountTypeEntity> optBankAccountType = bankAccountTypeRepository
				.findByCode(employeeReq.getBankAccountTypeCode());
		if (!optBankAccountType.isPresent()) {
			throw new Exception("Invalid Account Type. (Use SB for Savings and CUR for Current account)");
		}
		BankAccountTypeEntity bankAccountTypeEntity = optBankAccountType.get();

		EmployeeBankAccountInformationEntity bankInfo = new EmployeeBankAccountInformationEntity();
		bankInfo.setAccountName(employeeReq.getAccountName());
		bankInfo.setAccountNumber(employeeReq.getAccountNumber());
		bankInfo.setBankAccountType(bankAccountTypeEntity);
		bankInfo.setBankName(employeeReq.getBankName());
		bankInfo.setBranchName(employeeReq.getBranchName());
		bankInfo.setCurrentBalance(BigDecimal.ZERO);
		bankInfo.setIsDeleted(false);

		employeeEntity.setGrade(grade);
		employeeEntity.setBankAccount(bankInfo);
		employeeEntity.setAddress(employeeReq.getAddress());
		employeeEntity.setName(employeeReq.getName());
		employeeEntity.setMobile(employeeReq.getMobile());
		employeeEntity.setIsDeleted(false);

		employeeInformationRepository.save(employeeEntity);
	}

	public List<EmployeeInformationEntity> viewList() {

		List<EmployeeInformationEntity> responseList = employeeInformationRepository.findAll();
		if (responseList == null || responseList.isEmpty()) {
			responseList = new ArrayList<>();
		}
		return responseList;
	}

	public List<SalaryDetails> getCurrentBalanceOfEmployees() {
		List<SalaryDetails> list = new ArrayList<>();
		List<EmployeeInformationEntity> employeeList = employeeInformationRepository.findAll();
		if (employeeList != null && !employeeList.isEmpty()) {

			for (EmployeeInformationEntity employee : employeeList) {
				if (employee.getIsDeleted() != null && !employee.getIsDeleted()) {
					EmployeeBankAccountInformationEntity bankAccount = employee.getBankAccount();
					if (bankAccount != null) {
						BigDecimal basicSalary = employee.getGrade().getBasicSalary();
						if (basicSalary != null && basicSalary.compareTo(BigDecimal.ZERO) > 0) {
							SalaryDetails salaryDetails = new SalaryDetails(basicSalary);
							salaryDetails.setEmployeeID(employee.getId());
							salaryDetails.setEmployeeName(employee.getName());
							
							list.add(salaryDetails);
						}
					}
				}
			}
		}
		return list;
	}

	private Employee generateEmployee(Iterator<Cell> cellIterator) {
		Employee employee = new Employee();
		String val = null;
		while (cellIterator.hasNext()) {

			Cell currentCell = cellIterator.next();

			if (currentCell.getCellTypeEnum() == CellType.STRING) {
				val = currentCell.getStringCellValue();
			} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
				val = currentCell.getNumericCellValue() + "";
			}

			if (val == null || val.isEmpty()) {
				return null;
			}

			switch (currentCell.getColumnIndex()) {
			case 0: {
				employee.setName(val);
				break;
			}
			case 1: {
				employee.setMobile(val);
				break;
			}
			case 2: {
				employee.setAddress(val);
				break;
			}
			case 3: {
				if (val.contains(".")) {
					val = val.substring(0, val.indexOf("."));
				}
				employee.setGrade(Integer.valueOf(val));
				break;
			}
			case 4: {
				employee.setAccountName(val);
				break;
			}
			case 5: {
				employee.setAccountNumber(val);
				break;
			}
			case 6: {
				employee.setBankAccountTypeCode(val);
				break;
			}
			case 7: {
				employee.setBankName(val);
				break;
			}
			case 8: {
				employee.setBranchName(val);
				break;
			}
			}
		}
		return employee;
	}
}
