/**
 * 
 */
package brainstation.poc.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import brainstation.poc.model.EmployeeBankAccountInformationEntity;

@Repository
public interface EmployeeBankAccountInformationRepository
		extends CrudRepository<EmployeeBankAccountInformationEntity, Long> {
	Optional<EmployeeBankAccountInformationEntity> findByAccountNumberAndBankName(String accountNumber,
			String bankName);
}
