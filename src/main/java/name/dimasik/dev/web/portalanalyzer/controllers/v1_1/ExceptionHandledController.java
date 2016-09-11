package name.dimasik.dev.web.portalanalyzer.controllers.v1_1;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import name.dimasik.dev.web.portalanalyzer.util.Parser;
import name.dimasik.dev.web.portalanalyzer.util.WrongMessageFormatException;

import static name.dimasik.dev.web.portalanalyzer.controllers.v1_1.ErrorConstants.*;

/**
 * An interface for controller that should handle thrown exceptions.  
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public interface ExceptionHandledController {

	/**
	 * Handle {@link HttpMessageNotReadableException} and return user friendly message.
	 * @param e An exception thrown by the container or by the controller.
	 * @return 
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	default ResponseEntity<ErrorResponse> handleMessageNotReadableException(Exception e) {
		String description = e.getMessage().split(":")[0];
		ErrorResponse error = new ErrorResponse(
				MESSAGE_NOT_READABLE_ERROR_NAME,
				MESSAGE_NOT_READABLE_ERROR_MESSAGE, 
				description);
		return ResponseEntity.badRequest().body(error);
	}
	
	/**
	 * Handle {@link Parser.DaysCountFormatException} and return user friendly message.
	 * @param e An exception thrown by the controller.
	 * @return An error response.
	 */
	@ExceptionHandler(Parser.DaysCountFormatException.class)
	default ResponseEntity<ErrorResponse> handleWrongDaysCountFormatException(Exception e) {
		ErrorResponse error = new ErrorResponse(
				WRONG_DAYS_COUNT_FORMAT_ERROR_NAME, 
				WRONG_DAYS_COUNT_FORMAT_ERROR_MESSAGE, 
				e.getMessage());
		return ResponseEntity.badRequest().body(error);
	}
	
	/**
	 * Handle {@link WrongMessageFormatException} and return user friendly message.
	 * @param e An exception thrown by the controller.
	 * @return An error response.
	 */
	@ExceptionHandler(WrongMessageFormatException.class)
	default ResponseEntity<ErrorResponse> handleWrongMessageFormatException(Exception e) {
		ErrorResponse error = new ErrorResponse(
				WRONG_MESSAGE_FORMAT_ERROR_NAME, 
				WRONG_MESSAGE_FORMAT_ERROR_MESSAGE, 
				e.getMessage());
		return ResponseEntity.badRequest().body(error);
	}
}
