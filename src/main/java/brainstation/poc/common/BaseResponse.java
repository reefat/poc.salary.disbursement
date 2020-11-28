package brainstation.poc.common;

public class BaseResponse<T> {
	private T data;
	private int rc;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getRc() {
		return rc;
	}

	public void setRc(int rc) {
		this.rc = rc;
	}
}
