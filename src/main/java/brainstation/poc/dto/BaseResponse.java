package brainstation.poc.dto;

import brainstation.poc.utils.AppConstants;

public class BaseResponse {
	private boolean sucs;
	private int reasonCode;
	private String message;

	public boolean isSucs() {
		return sucs;
	}

	public int getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
		this.sucs = reasonCode == AppConstants.REASON_CODE_SUCCESS;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
