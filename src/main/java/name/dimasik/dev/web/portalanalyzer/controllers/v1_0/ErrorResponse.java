package name.dimasik.dev.web.portalanalyzer.controllers.v1_0;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class ErrorResponse {

	private String errorMessage;

	public ErrorResponse(String message) {
		this.errorMessage = message;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
