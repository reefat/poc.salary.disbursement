package brainstation.poc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brainstation.poc.model.SalaryDisbursementHistoryEntity;
import brainstation.poc.repository.SalaryDisbursementHistoryRepository;

@Service
public class SalaryDisbursementHistoryService {

	@Autowired
	private SalaryDisbursementHistoryRepository salaryDisbursementHistoryRepository;

	public void saveHistory(List<SalaryDisbursementHistoryEntity> list) {
		salaryDisbursementHistoryRepository.saveAll(list);
	}

	public List<SalaryDisbursementHistoryEntity> findAllByYearAndMonth(int year, String month) {
		return salaryDisbursementHistoryRepository.findAllByYearAndMonth(year, month);
	}

}
