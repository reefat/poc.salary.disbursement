package brainstation.poc.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@PropertySource("classpath:application.properties")
@Getter
@Setter
public class ApplicationPropertiesLoader {

	@Value("${company.main.bank.account.name:Brain Station}")
	private String companyMainBankAccountName;

	@Value("${company.main.bank.account.number:99999999999}")
	private String companyMainBankAccountNumber;

	@Value("${company.main.bank.account.bankname:Brac Bank Limited}")
	private String companyMainBankAccountBankName;

	@Value("${company.main.bank.account.branchname:Gulshan 1}")
	private String companyMainBankAccountBranchName;

	@Value("${company.main.bank.account.type:SB}")
	private String companyMainBankAccountType;
}
