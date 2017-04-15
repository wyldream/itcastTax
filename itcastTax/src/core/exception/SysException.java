package core.exception;
/**
 * 自定义异常处理错误
 * @author LBJ
 *
 */
public class SysException extends Exception{
	private String errorMsg;

	public SysException() {
		super();
	}

	public SysException(String message, Throwable cause) {
		super(message, cause);
		errorMsg = message;
	}

	public SysException(String message) {
		super(message);
		errorMsg = message;
	}

	public SysException(Throwable cause) {
		super(cause);
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
