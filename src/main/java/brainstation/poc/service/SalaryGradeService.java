package brainstation.poc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstation.poc.model.EmployeeInformationEntity;
import brainstation.poc.model.SalaryGradeEntity;
import brainstation.poc.repository.SalaryGradeRepository;

@Service
public class SalaryGradeService {

	@Autowired
	private SalaryGradeRepository salaryGradeRepository;

	public Optional<SalaryGradeEntity> findByGrade(int grade) {
		return salaryGradeRepository.findByGrade(grade);
	}

	public void save(SalaryGradeEntity entity) {
		salaryGradeRepository.save(entity);
	}

	@Transactional(rollbackFor = { Exception.class })
	public void setGradeWiseBasicSalary(BigDecimal basicSalary) {
		Pageable pageRequest = PageRequest.of(0, 1000, Sort.Direction.DESC, "grade");
		Page<SalaryGradeEntity> page = salaryGradeRepository.findAll(pageRequest);
		BigDecimal nextBasicSalary = basicSalary;
		if (page.hasContent()) {
			for (SalaryGradeEntity salaryGrade : page.getContent()) {
				salaryGrade.setBasicSalary(nextBasicSalary);
				salaryGradeRepository.save(salaryGrade);
				nextBasicSalary = nextBasicSalary.add(new BigDecimal(5000));
			}
		}
	}

	public List<SalaryGradeEntity> viewList() {

		List<SalaryGradeEntity> responseList = salaryGradeRepository.findAll();
		if (responseList == null || responseList.isEmpty()) {
			responseList = new ArrayList<>();
		}
		return responseList;
	}
}
