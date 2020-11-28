package brainstation.poc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brainstation.poc.model.EmployeeBankAccountInformationEntity;
import brainstation.poc.model.SalaryGradeEntity;
import brainstation.poc.repository.EmployeeBankAccountInformationRepository;
import brainstation.poc.utils.ApplicationPropertiesLoader;

@Service
public class BankAccountInformationService {

	@Autowired
	private ApplicationPropertiesLoader applicationPropertiesLoader;

	@Autowired
	private EmployeeBankAccountInformationRepository employeeBankAccountInformationRepository;

	public Optional<EmployeeBankAccountInformationEntity> getCompanyMainBankAccount() {
		String companyAccountNumber = applicationPropertiesLoader.getCompanyMainBankAccountNumber();
		String bankName = applicationPropertiesLoader.getCompanyMainBankAccountBankName();

		return employeeBankAccountInformationRepository.findByAccountNumberAndBankName(companyAccountNumber, bankName);
	}
	

	public void save(EmployeeBankAccountInformationEntity entity) {
		employeeBankAccountInformationRepository.save(entity);
	}
}
