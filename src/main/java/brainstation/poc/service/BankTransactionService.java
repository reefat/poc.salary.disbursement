package brainstation.poc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstation.poc.dto.BaseResponse;
import brainstation.poc.dto.SalaryDetails;
import brainstation.poc.model.EmployeeBankAccountInformationEntity;
import brainstation.poc.model.EmployeeInformationEntity;
import brainstation.poc.model.SalaryDisbursementHistoryEntity;
import brainstation.poc.utils.AppConstants;

@Service
public class BankTransactionService {

	@Autowired
	private BankAccountInformationService bankAccountInformationService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private SalaryDisbursementHistoryService salaryDisbursementHistoryService;

	public void addMoneyToCompanySalaryAccount(BigDecimal amount) throws Exception {
		Optional<EmployeeBankAccountInformationEntity> result = bankAccountInformationService
				.getCompanyMainBankAccount();
		if (!result.isPresent()) {
			throw new Exception("Company Main Account Not Found");
		}
		EmployeeBankAccountInformationEntity companyMainBankAccount = result.get();
		BigDecimal finalBalance = companyMainBankAccount.getCurrentBalance().add(amount);
		companyMainBankAccount.setCurrentBalance(finalBalance);
		bankAccountInformationService.save(companyMainBankAccount);
	}

	public BigDecimal getCompanyAccountBalance() throws Exception {
		Optional<EmployeeBankAccountInformationEntity> result = bankAccountInformationService
				.getCompanyMainBankAccount();
		if (!result.isPresent()) {
			throw new Exception("Company Main Account Not Found");
		}
		EmployeeBankAccountInformationEntity companyMainBankAccount = result.get();
		return companyMainBankAccount.getCurrentBalance();
	}

	@Transactional(rollbackFor = { Exception.class })
	public BaseResponse disburseSalary(int year, String month) {
		BaseResponse response = new BaseResponse();

		Optional<EmployeeBankAccountInformationEntity> result = bankAccountInformationService
				.getCompanyMainBankAccount();
		if (!result.isPresent()) {
			response.setReasonCode(AppConstants.REASON_CODE_COMPANY_BANK_INFO_NOT_FOUND);
			response.setMessage("Company Main Account Not Found");
			return response;
		}
		EmployeeBankAccountInformationEntity companyMainBankAccount = result.get();

		List<EmployeeInformationEntity> employeeList = employeeService.viewList();

		if (!employeeList.isEmpty()) {
			List<SalaryDisbursementHistoryEntity> alreadyDisbursedItems = salaryDisbursementHistoryService
					.findAllByYearAndMonth(year, month);
			if (alreadyDisbursedItems != null) {
				List<Long> alreadyDisbursedEmployeeIds = alreadyDisbursedItems.stream()
						.map(emp -> emp.getEmployeeId().getId()).collect(Collectors.toList());
				if (alreadyDisbursedEmployeeIds != null) {
					employeeList = employeeList.stream()
							.filter(employee -> !alreadyDisbursedEmployeeIds.contains(employee.getId()))
							.collect(Collectors.toList());
				}
			}
		}

		ArrayList<SalaryDetails> employeeSalaryList = new ArrayList<>();
		for (EmployeeInformationEntity employee : employeeList) {
			if (employee.getIsDeleted() != null && !employee.getIsDeleted()) {
				EmployeeBankAccountInformationEntity bankAccount = employee.getBankAccount();
				if (bankAccount != null) {
					BigDecimal basicSalary = employee.getGrade().getBasicSalary();
					if (basicSalary != null && basicSalary.compareTo(BigDecimal.ZERO) > 0) {
						SalaryDetails salaryDetails = new SalaryDetails(basicSalary);
						salaryDetails.setBankAccount(bankAccount);
						salaryDetails.setEmployee(employee);
						employeeSalaryList.add(salaryDetails);

						// Add Transaction Log
					} else {
						// log error - basicSalary empty
					}
				} else {
					// log error - bankAccount empty
				}
			}
		}

		List<SalaryDisbursementHistoryEntity> history = new ArrayList<>();

		if (!employeeSalaryList.isEmpty()) {
			employeeSalaryList.sort((p1, p2) -> p1.getTotalSalary().compareTo(p2.getTotalSalary()));
			for (SalaryDetails salaryDetails : employeeSalaryList) {
				try {
					EmployeeBankAccountInformationEntity bankAccount = salaryDetails.getBankAccount();
					BigDecimal companyMainBankAccountRemainingBalance = companyMainBankAccount.getCurrentBalance()
							.subtract(salaryDetails.getTotalSalary());
					if (companyMainBankAccountRemainingBalance.compareTo(BigDecimal.ZERO) < 0) {
						response.setReasonCode(AppConstants.REASON_CODE_INSUFFICIENT_BALANCE);
						response.setMessage("Insufficient Balance after " + ((!history.isEmpty() ? history.size() : 0))
								+ " salary disbursement");
						return response;
					}
					companyMainBankAccount.setCurrentBalance(companyMainBankAccountRemainingBalance);
					BigDecimal employeeCurrentBalance = bankAccount.getCurrentBalance() != null
							? bankAccount.getCurrentBalance()
							: BigDecimal.ZERO;

					employeeCurrentBalance = employeeCurrentBalance.add(salaryDetails.getTotalSalary());
					bankAccount.setCurrentBalance(employeeCurrentBalance);

					bankAccountInformationService.save(bankAccount);
					bankAccountInformationService.save(companyMainBankAccount);

					SalaryDisbursementHistoryEntity historyEntity = new SalaryDisbursementHistoryEntity();
					historyEntity.setBasicSalary(salaryDetails.getBasicSalary());
					historyEntity.setGrossSalary(salaryDetails.getTotalSalary());
					historyEntity.setHouseRent(salaryDetails.getHouseRent());
					historyEntity.setMedicalAllowance(salaryDetails.getMedicalAllowance());
					historyEntity.setMonth(month);
					historyEntity.setYear(year);
					historyEntity.setEmployeeId(salaryDetails.getEmployee());

					history.add(historyEntity);
				} finally {
					if (!history.isEmpty()) {
						salaryDisbursementHistoryService.saveHistory(history);
					}
				}
			}
		}

		response.setReasonCode(AppConstants.REASON_CODE_SUCCESS);
		response.setMessage("Total " + (!history.isEmpty() ? history.size() : 0) + " salary has been disbursed.");
		return response;
	}
}
