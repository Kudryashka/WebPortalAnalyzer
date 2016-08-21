package name.dimasik.dev.web.portalanalyzer.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import name.dimasik.dev.web.portalanalyzer.checklink.CheckLinkService;
import name.dimasik.dev.web.portalanalyzer.controllers.v1_0.EmptySearchQuery;
import name.dimasik.dev.web.portalanalyzer.search.QueryCountPair;
import name.dimasik.dev.web.portalanalyzer.search.SearchQueryService;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@RestController
@RequestMapping("/v1.0/")
public class RestController_v1_0 {
	
	private static final Logger logger = LoggerFactory.getLogger(RestController_v1_0.class);

	private CheckLinkService checkLinkService;
	private SearchQueryService searchService;
	
//	@Autowired
//	public void setCheckLinkService(CheckLinkService checkLinkService) {
//		this.checkLinkService = checkLinkService;
//	}
//	
//	@Autowired
//	public void setSearchService(SearchQueryService searchService) {
//		this.searchService = searchService;
//	}
//	
//	/* ********************************************
//	 * ******** PORTAL CHECK FUNCTIONALITY ********
//	 * ********************************************/
//
//	@PutMapping("/portalLinksCheck/run")
//	public void portalCheckRunImmediately() {
//		logger.info("Check portal run requested.");
//		checkLinkService.processCheckLinksOnPortalNow();
//	}
//	
//	@PutMapping("/portalLinksCheck/stop")
//	public void portalCheckStopCurrentCheck() {
//		logger.info("Check portal stop check requested.");
//	}
//	
//	@GetMapping("/portalLinksCheck/status")
//	public String portalCheckGetCurrentStatus() {
//		logger.info("Check portal status requested.");
//		return "ready";
//	}
//	
//	@GetMapping("/portalLinksCheck/schedulerRules")
//	@JsonView(SchedulerRule.View.class)
//	public List<SchedulerRule> portalCheckGetSchedulerRules() {
//		logger.info("Check portal scheduler rules requested.");
//		List<SchedulerRule> rules = new ArrayList<>();
//		rules.add(new SchedulerRule(0, "0 25 3 ? * 1", true));
//		rules.add(new SchedulerRule(1, "0 25 3 ? * 2", true));
//		rules.add(new SchedulerRule(2, "0 25 3 * * *", false));
//		return rules;
//	}
//	
//	@PutMapping("/portalLinksCheck/schedulerRules")
//	public void portalCheckAddSchedulerRule(@RequestBody SchedulerRule rule) {
//		logger.info("Check portal scheduler rule add request.");
//	}
//	
//	@DeleteMapping("/portalLinksCheck/schedulerRules/{id}")
//	public void portalCheckDeleteSchedulerRule(@PathVariable int id) {
//		logger.info("Check portal scheduler rule delete request.");
//	}
//	
//	
	
	
	public static class SchedulerRule {
		
		@JsonView(View.class)
		private int id;
		@JsonView(View.class)
		private String cronExpression;
		@JsonView(View.class)
		private boolean active;
		
		/**
		 * 
		 */
		public SchedulerRule() {
		}
		
		/**
		 * 
		 * @param id
		 * @param cronExpression
		 * @param active
		 */
		public SchedulerRule(int id, String cronExpression, boolean active) {
			this.id = id;
			this.cronExpression = cronExpression;
			this.active = active;
		}
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getCronExpression() {
			return cronExpression;
		}

		public void setCronExpression(String cronExpression) {
			this.cronExpression = cronExpression;
		}

		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}
		
		public interface View {};
	}
}
