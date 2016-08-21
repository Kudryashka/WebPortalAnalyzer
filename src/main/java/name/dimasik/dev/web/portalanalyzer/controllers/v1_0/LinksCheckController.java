package name.dimasik.dev.web.portalanalyzer.controllers.v1_0;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import name.dimasik.dev.web.portalanalyzer.checklink.CheckLinkReport;
import name.dimasik.dev.web.portalanalyzer.checklink.CheckLinkServiceStatus;
import name.dimasik.dev.web.portalanalyzer.schedule.SchedulerRule;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@RestController
@RequestMapping("/v1.0/portalLinksCheck")
@CrossOrigin
public class LinksCheckController {

	private static final Logger logger = LoggerFactory.getLogger(LinksCheckController.class);
	
	@PutMapping("/run")
	public void run() {
		logger.info("Request to run links check.");
		//TODO request run
	}
	
	@PutMapping("/stop")
	public void stop() {
		logger.info("Request to stop current links check.");
		//TODO request start
	}
	
	@GetMapping("/status")
	public LinksCheckStatusResponse getStatus() {
		logger.info("Request to get status.");
		//TODO 
		return new LinksCheckStatusResponse(CheckLinkServiceStatus.RUN);
	}
	
	@GetMapping("/schedulerRules")
	@JsonView(LinksCheckResponseView.class)
	public List<SchedulerRule> getSchedulerRules() {
		logger.info("Request to get scheduler rules.");
		//TODO with real
		List<SchedulerRule> rules = new ArrayList<>();
		SchedulerRule rule1 = new SchedulerRule();
		rule1.setId(1);
		rule1.setActive(true);
		rule1.setHours(10);
		SchedulerRule rule2 = new SchedulerRule();
		rule2.setId(2);
		rule2.setActive(false);
		rule2.setDayOfWeek(2);
		rules.add(rule1);
		rules.add(rule2);
		return rules;
	}
	
	@PutMapping("/schedulerRules")
	public ResponseEntity<String> addSchedulerRule(@RequestBody SchedulerRule rule) throws JsonProcessingException {
		logger.info("Request to add new scheduler rule.");
		if (!rule.isValid()) {
			ObjectMapper mapper = new ObjectMapper();
			ErrorResponse error = new ErrorResponse("Invalid request body.");
			return ResponseEntity.badRequest().body(mapper.writeValueAsString(error));
		}
		//TODO process
		return ResponseEntity.ok("");
	}
	
	@DeleteMapping("/schedulerRules/{id}")
	public void deleteSchedulerRule(@PathVariable String id) {
		logger.info("Request to delete scheduler rule with id: " + id);
		//TODO process
	}
	
	@GetMapping("/report/{days}")
	public ResponseEntity<String> getReport(@PathVariable String days) throws JsonProcessingException {
		logger.info("Request to get report. Days count: " + days);
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
		//TODO process 
		List<CheckLinkReport> report = new ArrayList<>();
		CheckLinkReport r1 = new CheckLinkReport();
		r1.setDate(new Date());
		r1.setErrorLinks(new ArrayList<>());
		r1.setOkLinks(new ArrayList<>());
		CheckLinkReport r2 = new CheckLinkReport();
		r2.setDate(new Date(new Date().getTime() - 10000));
		report.add(r1);
		report.add(r2);
		String json = mapper
						.writeValueAsString(report);
		return ResponseEntity.ok(json);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleError(Exception e) {
		ErrorResponse error = new ErrorResponse(e.getMessage().split(":")[0]);
		return ResponseEntity.badRequest().body(error);
	}
}
