package brainstation.poc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import brainstation.poc.model.EmployeeSalaryInformationEntity;

@Repository
public interface EmployeeSalaryInformationRepository extends CrudRepository<EmployeeSalaryInformationEntity, Long> {

}
