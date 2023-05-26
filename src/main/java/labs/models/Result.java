package labs.models;

public class Result {
	private int code;
	private String message;
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Result asError() {
		return code(1);
	}

	public Result code(int value) {
		this.code = value;
		return this;
	}

	public Result message(String message) {
		this.message = message;
		return this;
	}

	public Result data(Object data) {
		this.data = data;
		return this;
	}
}
