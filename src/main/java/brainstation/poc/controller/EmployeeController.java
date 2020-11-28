package brainstation.poc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import brainstation.poc.dto.EmployeeFileUpload;
import brainstation.poc.model.EmployeeInformationEntity;
import brainstation.poc.service.EmployeeService;

@Controller
@RequestMapping(value = "pages/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping(value = "view")
	public ModelMap viewEmployee() {
		ModelMap modelMap = new ModelMap();

		modelMap.addAttribute("employeeFileUpload", new EmployeeFileUpload());
		List<EmployeeInformationEntity> employeeList = employeeService.viewList();

		modelMap.addAttribute("employeeList", employeeList);

		return modelMap;
	}

	@GetMapping(value = "upload")
	public ModelAndView uploadEmployee() {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("pages/employee/upload");
		modelAndView.addObject("employeeFileUpload", new EmployeeFileUpload());

		return modelAndView;
	}

	@PostMapping(value = "upload")
	public String uploadEmployee(EmployeeFileUpload employeeFileUpload, RedirectAttributes redirectAttributes) {
		String fileLocation = "";
		File tempFile = null;
		boolean sucs = false;
		try {
			InputStream in = employeeFileUpload.getXlFile().getInputStream();
			File currDir = new File(".");
			String path = currDir.getAbsolutePath();
			fileLocation = path.substring(0, path.length() - 1) + employeeFileUpload.getXlFile().getOriginalFilename();
			FileOutputStream f = new FileOutputStream(fileLocation);
			int ch = 0;
			while ((ch = in.read()) != -1) {
				f.write(ch);
			}
			f.flush();
			f.close();
			tempFile = new File(fileLocation);
			employeeService.upload(tempFile);
			sucs = true;
			redirectAttributes.addFlashAttribute("message", "Message Uploaded Successfully");
			redirectAttributes.addFlashAttribute("employeeFileUpload", employeeFileUpload);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "Message Upload failed");
			redirectAttributes.addFlashAttribute("employeeFileUpload", employeeFileUpload);
		} finally {
			if (tempFile != null) {
				tempFile.delete();
			}
		}

		return sucs ? "redirect:/pages/employee/view" : "redirect:/pages/employee/upload";
	}

}
