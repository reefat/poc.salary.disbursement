package brainstation.poc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {

	private String name;
	private int grade;
	private String address;
	private String mobile;
	/** Bank Info. */
	private String bankAccountTypeCode;
	private String accountName;
	private String accountNumber;
	private String bankName;
	private String branchName;
}
