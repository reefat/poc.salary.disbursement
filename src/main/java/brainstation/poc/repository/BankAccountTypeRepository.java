package brainstation.poc.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import brainstation.poc.model.BankAccountTypeEntity;

@Repository
public interface BankAccountTypeRepository extends CrudRepository<BankAccountTypeEntity, Long> {
	Optional<BankAccountTypeEntity> findByCode(String code);
}
