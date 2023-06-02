package labs.models;

public class Result {
	private int code;
	private String message;
	private Object data;

	public Result asError() {
		return code(1);
	}

	public int code() {
		return this.code;
	}

	public Result code(int code) {
		this.code = code;
		return this;
	}

	public String message() {
		return this.message;
	}

	public Result message(String message) {
		this.message = message;
		return this;
	}

	public Object data() {
		return this.data;
	}

	public Result data(Object data) {
		this.data = data;
		return this;
	}
}
