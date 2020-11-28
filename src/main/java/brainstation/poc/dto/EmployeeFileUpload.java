package brainstation.poc.dto;

import org.springframework.web.multipart.MultipartFile;

public class EmployeeFileUpload {

	private MultipartFile xlFile;

	public MultipartFile getXlFile() {
		return xlFile;
	}

	public void setXlFile(MultipartFile xlFile) {
		this.xlFile = xlFile;
	}

}
