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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import brainstation.poc.dto.BalanceViewAndUpdate;
import brainstation.poc.dto.BaseResponse;
import brainstation.poc.dto.SalaryDetails;
import brainstation.poc.dto.SalaryDisbursement;
import brainstation.poc.service.BankTransactionService;
import brainstation.poc.service.EmployeeService;
import brainstation.poc.utils.AppConstants;

@Controller
@RequestMapping(value = "pages/banktransaction")
public class BankTransactionController {

	@Autowired
	private BankTransactionService bankTransactionService;

	@Autowired
	private EmployeeService employeeService;

	@GetMapping(value = "view")
	public ModelAndView viewCompanyAccountBalance() {
		ModelAndView modelAndView = new ModelAndView();
		BalanceViewAndUpdate balanceViewAndUpdate = new BalanceViewAndUpdate();
		try {
			BigDecimal currentBalance = bankTransactionService.getCompanyAccountBalance();
			List<SalaryDetails> salaryList = employeeService.getCurrentBalanceOfEmployees();

			balanceViewAndUpdate.setCurrentBalance(currentBalance);
			balanceViewAndUpdate.setSalaryList(salaryList);
			// companyAccount.set
		} catch (Exception e) {
			modelAndView.addObject("errorMessage", "Operation Failed");
		}

		modelAndView.setViewName("pages/banktransaction/view");
		modelAndView.addObject("balanceViewAndUpdate", balanceViewAndUpdate);

		return modelAndView;
	}

	@GetMapping(value = "addmoney")
	public ModelMap addmoney(RedirectAttributes redirectAttributes,
			@RequestParam(required = false, name = "incompleteDisbursement") final boolean incompleteDisbursement,
			@RequestParam(required = false, name = "month") final String month,
			@RequestParam(required = false, name = "year") final Integer year) {
		ModelMap modelMap = new ModelMap();
		System.out.println("###---month---" + month);
		System.out.println("###---year---" + year);
		System.out.println("###---incompleteDisbursement---" + incompleteDisbursement);
		BalanceViewAndUpdate balanceViewAndUpdate = new BalanceViewAndUpdate();
		balanceViewAndUpdate.setMonth(month);
		balanceViewAndUpdate.setIncompleteDisbursement(incompleteDisbursement);
		if (year != null) {
			balanceViewAndUpdate.setYear(year);
		}
		modelMap.addAttribute("companyAccountAddMoney", balanceViewAndUpdate);

		return modelMap;
	}

	// @GetMapping(value = "addmoney")
	// public ModelMap addmoney(RedirectAttributes redirectAttributes) {
	// ModelMap modelMap = new ModelMap();
	//
	// modelMap.addAttribute("companyAccountAddMoney", new BalanceViewAndUpdate());
	//
	// return modelMap;
	// }

	@PostMapping("/addmoney")
	public String addMoneyToCompanySalaryAccount(
			@ModelAttribute(value = "companyAccountAddMoney") BalanceViewAndUpdate balanceViewAndUpdate,
			RedirectAttributes redirectAttributes) {

		try {
			if (balanceViewAndUpdate.getAddedBalanceAmount() != null
					&& balanceViewAndUpdate.getAddedBalanceAmount().compareTo(BigDecimal.ZERO) > 0) {
				bankTransactionService.addMoneyToCompanySalaryAccount(balanceViewAndUpdate.getAddedBalanceAmount());
				if (balanceViewAndUpdate.isIncompleteDisbursement()) {
					BaseResponse response = bankTransactionService.disburseSalary(balanceViewAndUpdate.getYear(),
							balanceViewAndUpdate.getMonth());
					if (response.isSucs()) {
						redirectAttributes.addFlashAttribute("message", "Salary Disbursed Successfully");
					} else {
						redirectAttributes.addFlashAttribute("incompleteDisbursement",
								response.getReasonCode() == AppConstants.REASON_CODE_INSUFFICIENT_BALANCE);
						redirectAttributes.addFlashAttribute("errorMessage", response.getMessage());
						if (response.getReasonCode() == AppConstants.REASON_CODE_INSUFFICIENT_BALANCE) {
							
							redirectAttributes.addFlashAttribute("incompleteDisbursement", true);
							redirectAttributes.addFlashAttribute("month", balanceViewAndUpdate.getMonth());
							redirectAttributes.addFlashAttribute("year", balanceViewAndUpdate.getYear());

							return "redirect:/pages/banktransaction/addmoney";
						} else {
							return "redirect:/pages/banktransaction/salarydisburse";
						}
					}
				} else {
					redirectAttributes.addFlashAttribute("message", "Money added to Company A/C Successfully");
				}

			} else {
				redirectAttributes.addFlashAttribute("errorMessage", "Amount of money must be greater than zero.");
				return "redirect:/pages/banktransaction/addmoney";
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Operation Failed");
			return "redirect:/pages/banktransaction/addmoney";
		}

		return "redirect:/pages/banktransaction/view";
	}

	@GetMapping(value = "salarydisburse")
	public ModelMap disburseSalary() {
		ModelMap modelMap = new ModelMap();

		modelMap.addAttribute("salaryDisbursement", new SalaryDisbursement());

		return modelMap;
	}

	@PostMapping("/salarydisburse")
	public String disburseSalary(@ModelAttribute(value = "salaryDisbursement") SalaryDisbursement salaryDisbursement,
			RedirectAttributes redirectAttributes) {
		try {
			if (salaryDisbursement.getMonth() == null || salaryDisbursement.getYear() == null
					|| salaryDisbursement.getYear() < 2000 || salaryDisbursement.getYear() > 3000) {
				redirectAttributes.addFlashAttribute("errorMessage", "Invalid Data.");
				return "redirect:/pages/banktransaction/salarydisburse";
			} else {
				BaseResponse response = bankTransactionService.disburseSalary(salaryDisbursement.getYear(),
						salaryDisbursement.getMonth());
				if (response.isSucs()) {
					redirectAttributes.addFlashAttribute("message", "Salary Disbursed Successfully");
				} else {
					redirectAttributes.addFlashAttribute("incompleteDisbursement",
							response.getReasonCode() == AppConstants.REASON_CODE_INSUFFICIENT_BALANCE);
					redirectAttributes.addFlashAttribute("errorMessage", response.getMessage());
					if (response.getReasonCode() == AppConstants.REASON_CODE_INSUFFICIENT_BALANCE) {

						redirectAttributes.addAttribute("month", salaryDisbursement.getMonth());
						redirectAttributes.addAttribute("year", salaryDisbursement.getYear());

						return "redirect:/pages/banktransaction/addmoney";
					} else {
						return "redirect:/pages/banktransaction/salarydisburse";
					}

				}
			}
		} catch (Exception e) {
			String errorMsg;
			if (e.getMessage() != null) {
				errorMsg = e.getMessage();
			} else {
				errorMsg = "Operation Failed";
			}
			redirectAttributes.addFlashAttribute("errorMessage", errorMsg);
			return "redirect:/pages/banktransaction/salarydisburse";
		}

		return "redirect:/pages/banktransaction/view";
	}
}
