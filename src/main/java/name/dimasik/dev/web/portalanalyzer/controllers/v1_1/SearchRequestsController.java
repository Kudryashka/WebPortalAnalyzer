package name.dimasik.dev.web.portalanalyzer.controllers.v1_1;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import name.dimasik.dev.web.portalanalyzer.search.QueryCountPair;
import name.dimasik.dev.web.portalanalyzer.search.SearchQueryService;
import name.dimasik.dev.web.portalanalyzer.util.Parser;
import name.dimasik.dev.web.portalanalyzer.util.Parser.DaysCountFormatException;
import name.dimasik.dev.web.portalanalyzer.util.WrongMessageFormatException;

/**
 * This controller handle requests to process searches statistic.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@RestController("SearchRequestsController_v1.1")
@RequestMapping("/api/v1.1/searchRequests")
public class SearchRequestsController implements ExceptionHandledController {

	private static final Logger logger = LoggerFactory.getLogger(SearchRequestsController.class);
	
	private SearchQueryService searchRequestsService;
	
	/**
	 * Used for service injection.
	 */
	@Autowired
	public void setSearchRequestsService(SearchQueryService searchRequestsService) {
		this.searchRequestsService = searchRequestsService;
	}

	/**
	 * Register new search request.
	 */
	@PutMapping
	public ResponseEntity<String> registerRequest(@RequestBody JsonSearchRequest request) 
			throws WrongMessageFormatException {
		logger.info("Request to register new search request.");
		
		String query = request.getQuery();
		if (query == null) {
			throw new WrongMessageFormatException("Message does not contain search request.");
		}
		if (query.isEmpty()) {
			throw new WrongMessageFormatException("Search query is empty.");
		}
		
		searchRequestsService.registerQuery(query);
		return ResponseEntity.ok("");
	}
	
	/**
	 * Search requests report for <code>{days}</code> days.
	 */
	@GetMapping("/report/{days}")
	public ResponseEntity<List<JsonSearchReportEntity>> getReport(@PathVariable("days") String daysStr) 
			throws DaysCountFormatException {
		logger.info("Search requests report requested.");
		
		int days = Parser.parseDaysCount(daysStr);
		List<JsonSearchReportEntity> report = new ArrayList<>();
		
		//TODO reimplement
		List<QueryCountPair> queries = searchRequestsService.getQueries(days);
		queries.forEach(pair -> report.add(new JsonSearchReportEntity(pair.getQuery(), pair.getCount())));
		
		return ResponseEntity.ok(report);
	}
	
	/**
	 * JSON representation of search request.
	 */
	public static class JsonSearchRequest {
		
		private String query;

		public String getQuery() {
			return query;
		}

		public void setQuery(String query) {
			this.query = query;
		}
	}
	
	/**
	 * JSON representation of search requests report entity.
	 */
	public static class JsonSearchReportEntity {
		
		private final String query;
		private final int count;
		
		private JsonSearchReportEntity(String query, int count) {
			this.query = query;
			this.count = count;
		}

		public String getQuery() {
			return query;
		}

		public int getCount() {
			return count;
		}
	}
}
