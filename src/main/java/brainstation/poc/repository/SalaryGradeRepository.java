package brainstation.poc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import brainstation.poc.model.SalaryGradeEntity;

@Repository
public interface SalaryGradeRepository extends CrudRepository<SalaryGradeEntity, Long> {
	Optional<SalaryGradeEntity> findByGrade(int grade);
	Page<SalaryGradeEntity> findAll(Pageable page);
	List<SalaryGradeEntity> findAll();
}
