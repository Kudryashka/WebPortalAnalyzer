package name.dimasik.dev.web.portalanalyzer.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import name.dimasik.dev.web.portalanalyzer.search.SearchQueryService;

/**
 * 
 * Controller for requests for simple test pages. Should be removed in future.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@Controller
@RequestMapping("/api/")
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	private SearchQueryService searchService;
	
	@Autowired
	public void setSearchService(SearchQueryService searchService) {
		this.searchService = searchService;
	}

	@GetMapping("/testHome")
	public String getTestHome() {
		return "testHome";
	}
	
	@GetMapping("/testPageWithLinks")
	public String getTestPageWithLinks() {
		logger.info("getTestPageWithLinks()");
		
		return "testPageWithLinks";
	}
	
	@GetMapping("/testUserInfo")
	public String getTestUserInfo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		logger.info("getTestUsetInfo()");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/api/http/processRawUserRequest");
		dispatcher.include(request, response);
		
		return "testUserInfo";
	}
	
	@GetMapping("/testAddSearchQuery")
	public String testAddSearchQuery(Model model) {
		logger.info("testAddSearchQuery");
		model.addAttribute("search", new SearchForm());
		return "testSearch";
	}
	
	@PostMapping("/testAddSearchQuery")
	public String submitSearchQuery(@ModelAttribute("search") SearchForm query) {
		logger.info("submit SearchQuery");
		searchService.registerQuery(query.query);
		return "testSearch";
	}
	
	public static class SearchForm {
		
		private String query;

		public String getQuery() {
			return query;
		}

		public void setQuery(String query) {
			this.query = query;
		}
	}
}
