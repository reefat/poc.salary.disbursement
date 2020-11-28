package brainstation.poc.configuration;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import brainstation.poc.model.BankAccountTypeEntity;
import brainstation.poc.model.EmployeeBankAccountInformationEntity;
import brainstation.poc.model.SalaryGradeEntity;
import brainstation.poc.repository.BankAccountTypeRepository;
import brainstation.poc.service.BankAccountInformationService;
import brainstation.poc.service.BankAccountTypeService;
import brainstation.poc.service.SalaryGradeService;
import brainstation.poc.utils.ApplicationPropertiesLoader;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;

	private BankAccountTypeService bankAccountTypeService;
	private SalaryGradeService salaryGradeService;
	private BankAccountInformationService bankAccountInformationService;
	private BankAccountTypeRepository bankAccountTypeRepository;
	private ApplicationPropertiesLoader applicationPropertiesLoader;

	public SetupDataLoader(BankAccountTypeService bankAccountTypeService, SalaryGradeService salaryGradeService,
			ApplicationPropertiesLoader applicationPropertiesLoader,
			BankAccountInformationService bankAccountInformationService,
			BankAccountTypeRepository bankAccountTypeRepository) {
		this.bankAccountTypeService = bankAccountTypeService;
		this.salaryGradeService = salaryGradeService;
		this.bankAccountInformationService = bankAccountInformationService;
		this.applicationPropertiesLoader = applicationPropertiesLoader;
		this.bankAccountTypeRepository = bankAccountTypeRepository;
	}

	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}

		createBankAccountTypeIfNotFound("Savings", "SB");
		createBankAccountTypeIfNotFound("Current", "CUR");

		for (int grade = 1; grade <= 6; grade++) {
			createSalaryGradeIfNotFound(grade);
		}
		
		createCompanyMainBankAccountIfNotFound();

		alreadySetup = true;
	}

	@Transactional
	BankAccountTypeEntity createBankAccountTypeIfNotFound(final String name, final String code) {
		BankAccountTypeEntity entity;
		Optional<BankAccountTypeEntity> result = bankAccountTypeService.findByCode(code);
		if (!result.isPresent()) {
			entity = new BankAccountTypeEntity();
			entity.setName(name);
			entity.setCode(code);
			bankAccountTypeService.save(entity);
		} else {
			entity = result.get();
		}
		return entity;
	}

	@Transactional
	SalaryGradeEntity createSalaryGradeIfNotFound(final int grade) {
		SalaryGradeEntity entity;
		Optional<SalaryGradeEntity> result = salaryGradeService.findByGrade(grade);
		if (!result.isPresent()) {
			entity = new SalaryGradeEntity();
			entity.setGrade(grade);
			salaryGradeService.save(entity);
		} else {
			entity = result.get();
		}
		return entity;
	}

	@Transactional
	EmployeeBankAccountInformationEntity createCompanyMainBankAccountIfNotFound() {
		EmployeeBankAccountInformationEntity entity;
		Optional<EmployeeBankAccountInformationEntity> result = bankAccountInformationService
				.getCompanyMainBankAccount();
		if (!result.isPresent()) {
			entity = new EmployeeBankAccountInformationEntity();
			entity.setAccountName(applicationPropertiesLoader.getCompanyMainBankAccountName());
			entity.setAccountNumber(applicationPropertiesLoader.getCompanyMainBankAccountNumber());
			entity.setBankName(applicationPropertiesLoader.getCompanyMainBankAccountBankName());
			entity.setBranchName(applicationPropertiesLoader.getCompanyMainBankAccountBranchName());
			entity.setCurrentBalance(BigDecimal.ZERO);
			entity.setIsDeleted(false);

			Optional<BankAccountTypeEntity> optBankAccountType = bankAccountTypeRepository
					.findByCode(applicationPropertiesLoader.getCompanyMainBankAccountType());
			
			BankAccountTypeEntity bankAccountTypeEntity = optBankAccountType.get();
			entity.setBankAccountType(bankAccountTypeEntity);

			bankAccountInformationService.save(entity);
		} else {
			entity = result.get();
		}
		return entity;
	}
}