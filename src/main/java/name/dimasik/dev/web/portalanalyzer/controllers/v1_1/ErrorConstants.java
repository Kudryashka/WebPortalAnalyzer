package name.dimasik.dev.web.portalanalyzer.controllers.v1_1;

import org.springframework.http.converter.HttpMessageNotReadableException;

import name.dimasik.dev.web.portalanalyzer.util.Parser;
import name.dimasik.dev.web.portalanalyzer.util.WrongMessageFormatException;

/**
 * Set of constants for the errors handled by the current API version.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public final class ErrorConstants {

	/**
	 * Name of the error caused by {@link HttpMessageNotReadableException}
	 */
	public static final String MESSAGE_NOT_READABLE_ERROR_NAME = "MESSAGE_NOT_READABLE_ERROR";
	
	/**
	 * Default message of the error caused by {@link HttpMessageNotReadableException}
	 */
	public static final String MESSAGE_NOT_READABLE_ERROR_MESSAGE = "Message is not readable.";
	
	/**
	 * Name of the error caused by {@link Parser.DaysCountFormatException}
	 */
	public static final String WRONG_DAYS_COUNT_FORMAT_ERROR_NAME = "WRONG_DAYS_COUNT_FORMAT_ERROR";
	
	/**
	 * Default message of the error caused by {@link Parser.DaysCountFormatException} 
	 */
	public static final String WRONG_DAYS_COUNT_FORMAT_ERROR_MESSAGE = "Days count format is wrong.";
	
	/**
	 * Name of the error caused by {@link WrongMessageFormatException}
	 */
	public static final String WRONG_MESSAGE_FORMAT_ERROR_NAME = "WRONG_MESSAGE_FORMAT";
	
	/**
	 * Default message of the error caused by {@link WrongMessageFormatException} 
	 */
	public static final String WRONG_MESSAGE_FORMAT_ERROR_MESSAGE = "Message format is wrong.";
}
