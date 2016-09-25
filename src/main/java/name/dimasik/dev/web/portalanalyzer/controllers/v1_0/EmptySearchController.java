package name.dimasik.dev.web.portalanalyzer.controllers.v1_0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import name.dimasik.dev.web.portalanalyzer.search.SearchQueryService;

/**
 * TODO delete this controller
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@RestController
@RequestMapping("/v1.0/portalEmptySearch")
@Deprecated
public class EmptySearchController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmptySearchController.class);
	
	private SearchQueryService searchQueryService;
	
	@Autowired
	public void setSearchQueryService(SearchQueryService searchQueryService) {
		this.searchQueryService = searchQueryService;
	}

	@PutMapping
	public ResponseEntity<String> registerRequest(@RequestBody EmptySearchQuery body) throws JsonProcessingException {
		logger.info("Request to register new search request.");
		
		String query = body.getQuery();
		if (query == null || query.isEmpty()) {
			logger.error("Illegal request. Request body does not contain request.");
			ObjectMapper mapper = new ObjectMapper();
			ErrorResponse error = new ErrorResponse("Search query is empty");
			return ResponseEntity.badRequest().body(mapper.writeValueAsString(error));
		}
		
		searchQueryService.registerQuery(query);
		return ResponseEntity.ok().body("");
	}
	
	@GetMapping("/report/{days}")
	public ResponseEntity<String> getReport(@PathVariable String days) throws JsonProcessingException {
		logger.info("Request to get report for empty searches. Days count: " + days);
		ObjectMapper mapper = new ObjectMapper();
		
		int daysCount;
		try {
			daysCount = Integer.parseInt(days);
			if (daysCount < 0) {
				ErrorResponse error = new ErrorResponse("Days count can not be less then zero.");
				return ResponseEntity.badRequest().body(mapper.writeValueAsString(error));
			}
		} catch (NumberFormatException e) {
			logger.error("Illegal request. Wrong days count format.");
			ErrorResponse error = new ErrorResponse("Wrong days count format");
			return ResponseEntity.badRequest().body(mapper.writeValueAsString(error));
		}
		
		String json = mapper
						.writerWithView(EmptySearchView.class)
						.writeValueAsString(searchQueryService.getQueries(daysCount));
		return ResponseEntity.ok(json);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleError(Exception e) {
		ErrorResponse error = new ErrorResponse(e.getMessage().split(":")[0]);
		return ResponseEntity.badRequest().body(error);
	}
}
