package name.dimasik.dev.web.portalanalyzer.controllers.v1_0;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

import name.dimasik.dev.web.portalanalyzer.checklink.CheckLinkService;
import name.dimasik.dev.web.portalanalyzer.checklink.CheckLinkServiceStatus;
import name.dimasik.dev.web.portalanalyzer.checklink.LinkInfo;
import name.dimasik.dev.web.portalanalyzer.controllers.v1_0.LinksCheckReport.LinksCheckReportDetail;
import name.dimasik.dev.web.portalanalyzer.controllers.v1_0.LinksCheckReport.LinksCheckReportDetailSummary;
import name.dimasik.dev.web.portalanalyzer.controllers.v1_0.LinksCheckReport.LinksCheckReportSummary;
import name.dimasik.dev.web.portalanalyzer.schedule.SchedulerRule;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@RestController
@RequestMapping("/api/v1.0/portalLinksCheck")
public class LinksCheckController {

	private static final Logger logger = LoggerFactory.getLogger(LinksCheckController.class);
	
	private CheckLinkService checkLinkService;
	
	@Autowired
	public void setCheckLinkService(CheckLinkService checkLinkService) {
		logger.info("Service initialized.");
		this.checkLinkService = checkLinkService;
	}

	@PutMapping("/run")
	public void run() {
		logger.info("Request to run links check.");
		//TODO request run
		checkLinkService.processCheckLinksOnPortalNow();
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
		return new LinksCheckStatusResponse(CheckLinkServiceStatus.FREE);
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
		
		//TODO
		HashMap<Date, List<LinkInfo>>results = checkLinkService.getCheckLinkResults();
		
		LinksCheckReport report = new LinksCheckReport();
		LinksCheckReportSummary summary = new LinksCheckReportSummary();
		List<LinksCheckReportDetail> details = new ArrayList<>();
		report.setSummary(summary);
		report.setDetails(details);
		
		int errorLinksCount = 0;
		int okLinksCount = 0;
		int redirectLinksCount = 0;
		int unreachableLinksCount = 0;
		
		DateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		for (Entry<Date, List<LinkInfo>> entry : results.entrySet()) {
			LinksCheckReportDetail detail = new LinksCheckReportDetail();
			
			detail.setDate(df.format(entry.getKey()));//TODO
			
			List<LinksCheckReport.Link> errorLinks = new ArrayList<>();
			List<LinksCheckReport.Link> okLinks = new ArrayList<>();
			List<LinksCheckReport.RedirectLink> redirectLinks = new ArrayList<>();
			List<LinksCheckReport.UnreachableLink> unreachableLinks = new ArrayList<>();
			
			int detailErrorLinksCount = 0;
			int detailOkLinksCount = 0;
			int detailUnreachableLinksCount = 0;
			int detailRedirectLinksCount = 0;
			
			for (LinkInfo info : entry.getValue()) {
				switch(info.linkStatus) {
				case REDIRECT:
					LinksCheckReport.RedirectLink redLink = new LinksCheckReport.RedirectLink();
					redLink.setType(info.type.name());
					redLink.setTarget(info.targetUrl);
					redLink.setResponseCode(info.responseCode);
					redLink.setRedirectUrl(info.redirectTarget);
					redLink.setLocation(info.pageUrl);
					
					redirectLinksCount++;
					detailRedirectLinksCount++;
					redirectLinks.add(redLink);
					break;
				case ERROR:
					LinksCheckReport.Link errorLink = new LinksCheckReport.Link();
					errorLink.setType(info.type.name());
					errorLink.setTarget(info.targetUrl);
					errorLink.setResponseCode(info.responseCode);
					errorLink.setLocation(info.pageUrl);
					
					errorLinksCount++;
					detailErrorLinksCount++;
					errorLinks.add(errorLink);
					break;
				case UNREACHABLE:
					LinksCheckReport.UnreachableLink unreachableLink = new LinksCheckReport.UnreachableLink();
					unreachableLink.setType(info.type.name());
					unreachableLink.setTarget(info.targetUrl);
					unreachableLink.setLocation(info.pageUrl);
					
					unreachableLinksCount++;
					detailUnreachableLinksCount++;
					unreachableLinks.add(unreachableLink);
					break;
				default:
					LinksCheckReport.Link okLink = new LinksCheckReport.Link();
					okLink.setType(info.type.name());
					okLink.setTarget(info.targetUrl);
					okLink.setResponseCode(info.responseCode);
					okLink.setLocation(info.pageUrl);
					
					okLinksCount++;
					detailOkLinksCount++;
					okLinks.add(okLink);
					break;	
				}
			}
			
			detail.setErrorLinks(errorLinks);
			detail.setOkLinks(okLinks);
			detail.setRedirectLinks(redirectLinks);
			detail.setUnreachableLinks(unreachableLinks);
			
			LinksCheckReportDetailSummary detailSummary = new LinksCheckReportDetailSummary();
			detailSummary.setErrorLinksCount(detailErrorLinksCount);
			detailSummary.setOkLinksCount(detailOkLinksCount);
			detailSummary.setUnreachableLinksCount(detailUnreachableLinksCount);
			detailSummary.setRedirectLinksCount(detailRedirectLinksCount);
			detail.setSummary(detailSummary);
			details.add(detail);
		}
		
		
		summary.setCheckCount(results.size());
		summary.setErrorLinksCount(errorLinksCount);
		summary.setOkLinksCount(okLinksCount);
		summary.setRedirectLinksCount(redirectLinksCount);
		summary.setUnreachableLinksCount(unreachableLinksCount);
				
		String json = mapper
						.writeValueAsString(report);
		logger.info(json);
		return ResponseEntity.ok(json);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleError(Exception e) {
		ErrorResponse error = new ErrorResponse(e.getMessage().split(":")[0]);
		return ResponseEntity.badRequest().body(error);
	}
}
