package brainstation.poc.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import brainstation.poc.dto.GradeBasicPay;
import brainstation.poc.model.SalaryGradeEntity;
import brainstation.poc.service.SalaryGradeService;

@Controller
@RequestMapping(value = "pages/grade")
public class GradeController {

	@Autowired
	private SalaryGradeService salaryGradeService;

	@GetMapping(value = "view")
	public ModelMap viewGrade() {
		ModelMap modelMap = new ModelMap();

		modelMap.addAttribute("gradeBasicPay", new GradeBasicPay());
		List<SalaryGradeEntity> gradeList = salaryGradeService.viewList();

		modelMap.addAttribute("gradeList", gradeList);

		return modelMap;
	}

	@GetMapping(value = "basicsalary")
	public ModelMap basicsalary() {
		ModelMap modelMap = new ModelMap();

		modelMap.addAttribute("gradeBasicPay", new GradeBasicPay());

		return modelMap;
	}

	@PostMapping("/basicsalary")
	public String addTopic(@ModelAttribute(value = "gradeBasicPay") GradeBasicPay gradeBasicPay,
			RedirectAttributes redirectAttributes) {

		try {
			if (gradeBasicPay.getBasicSalary() != null
					&& gradeBasicPay.getBasicSalary().compareTo(BigDecimal.ZERO) > 0) {
				salaryGradeService.setGradeWiseBasicSalary(gradeBasicPay.getBasicSalary());
				redirectAttributes.addFlashAttribute("message", "Basic Salary Updated Successfully");
			} else {
				redirectAttributes.addFlashAttribute("errorMessage", "Basic Salary must be greater than zero.");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Operation Failed");
		}

		return "redirect:/pages/grade/view";
	}

}
