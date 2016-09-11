package name.dimasik.dev.web.portalanalyzer.controllers.v1_1;

/**
 * Represents an error as REST entity.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class ErrorResponse {

	private String name;
	private String message;
	private String description;
	
	/**
	 * Creates a new instance of {@link ErrorResponse}
	 * 
	 * @param name Name of the error
	 * @param message Short message that describes the error
	 * @param description Detail description of the error
	 */
	public ErrorResponse(String name, String message, String description) {
		this.name = name;
		this.message = message;
		this.description = description;
	}

	/**
	 * Get a canonical name of the error. 
	 * @return Name of the error
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get a short message that described the error. 
	 * @return Message of the error
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Get a description for the error.
	 * @return Description of the error
	 */
	public String getDescription() {
		return description;
	}
}
