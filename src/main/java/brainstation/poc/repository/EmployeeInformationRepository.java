package brainstation.poc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import brainstation.poc.model.EmployeeInformationEntity;

@Repository
public interface EmployeeInformationRepository extends CrudRepository<EmployeeInformationEntity, Long> {
	List<EmployeeInformationEntity> findAll();
}
