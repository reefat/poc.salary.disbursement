package brainstation.poc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import brainstation.poc.model.SalaryDisbursementHistoryEntity;

public interface SalaryDisbursementHistoryRepository extends CrudRepository<SalaryDisbursementHistoryEntity, Long> {
	List<SalaryDisbursementHistoryEntity> findAllByYearAndMonth(int year, String month);
}
