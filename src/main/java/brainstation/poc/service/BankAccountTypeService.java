package brainstation.poc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brainstation.poc.model.BankAccountTypeEntity;
import brainstation.poc.repository.BankAccountTypeRepository;

@Service
public class BankAccountTypeService {

	@Autowired
	private BankAccountTypeRepository bankAccountTypeRepository;

	public Optional<BankAccountTypeEntity> findByCode(String code) {
		return bankAccountTypeRepository.findByCode(code);
	}

	public void save(BankAccountTypeEntity entity) {
		bankAccountTypeRepository.save(entity);
	}
}
