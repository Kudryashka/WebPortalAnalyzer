package name.dimasik.dev.web.portalanalyzer.controllers.v1_1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import name.dimasik.dev.web.portalanalyzer.checklink.CheckLinkService;
import name.dimasik.dev.web.portalanalyzer.checklink.CheckedLink;
import name.dimasik.dev.web.portalanalyzer.checklink.LinksCheck;
import name.dimasik.dev.web.portalanalyzer.util.Parser;
import name.dimasik.dev.web.portalanalyzer.util.Parser.DaysCountFormatException;
import name.dimasik.dev.web.portalanalyzer.util.WrongIDFormatException;

/**
 * This controller handles requests to process links check operations.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@RestController("LinksCheckController_v1.1")
@RequestMapping("/api/v1.1/linksCheck")
public class LinksCheckController implements ExceptionHandledController {

	private static final Logger logger = LoggerFactory.getLogger(LinksCheckController.class);
	
	private static ThreadLocal<DateFormat> DATE_FORMAT_TL = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("HH:mm dd/MM/yyyy");
		}
	};
	
	private CheckLinkService checkLinkService;
	
	/**
	 * Using for service injection.
	 */
	@Autowired
	public void setCheckLinkService(CheckLinkService checkLinkService) {
		this.checkLinkService = checkLinkService;
	}
	
	/**
	 * Request to run check of links on the portal.
	 */
	@PutMapping("/run")
	public ResponseEntity<String> run() {
		logger.info("run()");
		checkLinkService.processCheckLinksOnPortalNow();
		return ResponseEntity.ok("");
	}
	
	/**
	 * Request to stop current check of links on the portal.
	 */
	@PutMapping("/stop")
	public ResponseEntity<String> stop() {
		logger.info("stop()");
		//TODO
		return ResponseEntity.ok("");
	}
	
	/**
	 * Get current status of the service.
	 */
	@GetMapping("/status")
	public ResponseEntity<JsonStatus> getStatus() {
		logger.info("status()");
		boolean isProcessing = checkLinkService.isProcessing();
		return ResponseEntity.ok(
				new JsonStatus(isProcessing ? JsonStatus.Status.RUNNING : JsonStatus.Status.FREE));
	}
	
	/**
	 * Get report of check of links on the portal for specified period.
	 */
	@GetMapping("/report/{days}")
	public ResponseEntity<JsonReport> getReport(@PathVariable("days") String daysStr) throws DaysCountFormatException {
		logger.info("report() Days count: " + daysStr);
		
		int days = Parser.parseDaysCount(daysStr);
		List<LinksCheck> checks = checkLinkService.getCheckResults(days);
		
		return ResponseEntity.ok(JsonReport.fromChecksList(checks));
	}
	
	/**
	 * Get details about some concrete check of links on the portal.
	 */
	@GetMapping("/details/{id}")
	public ResponseEntity<JsonDetails> getDetails(@PathVariable("id") String idStr) throws WrongIDFormatException {
		logger.info("details() Check ID: " + idStr);
		
		int id = Parser.parseIntegerID(idStr);
		List<CheckedLink> links = checkLinkService.getCheckDetails(id);
		
		//TODO
		List<JsonDetails.Link> detLinks = new ArrayList<>();
		links.forEach(l -> detLinks.add(new JsonDetails.Link(
				JsonDetails.Link.Status.valueOf(l.getLinkStatus().toString()), l.getLinkType().toString(), 
				l.getLocation(), l.getTarget(), l.getResponseCode(), l.getRedirectUrl())));
		JsonDetails json = new JsonDetails(id, new Date(), new Date(), detLinks);
		
		return ResponseEntity.ok(json);
	}
	
	//TODO Add scheduler operations
	
	/**
	 * JSON representation of the service status
	 */
	public static final class JsonStatus {
		
		private final Status status;
		
		private JsonStatus(Status status) {
			this.status = status;
		}
		
		public Status getStatus() {
			return status;
		}

		private enum Status {
			RUNNING, FREE
		}
	}
	
	/**
	 * JSON representation of the report
	 */
	public static final class JsonReport {
		
		private final Summary summary;
		private final List<Details> details;
		
		private static JsonReport fromChecksList(List<LinksCheck> checks) {
			//construct details
			List<Details> details = new ArrayList<>();
			for (LinksCheck c : checks) {
				int ok = 0, error = 0, unreachable = 0, redirect = 0;
				for (CheckedLink link : c.getCheckedLinks()) {
					switch (link.getLinkStatus()) {
					case ERROR: error++; break;
					case OK: ok++; break;
					case REDIRECT: redirect++; break;
					case UNREACHABLE: unreachable++; break;
					}
				}
				Details d = new Details(c.getId(), c.getStartTime(), c.getEndTime(), 
						ok, error, unreachable, redirect);
				details.add(d);
			}
			//construct summary
			int ok = 0, error = 0, unreachable = 0, redirect = 0;
			for (Details d : details) {
				ok += d.ok;
				error += d.error;
				unreachable += d.unreachable;
				redirect += d.redirect;
			}
			int total = checks.size();
			Summary summary = new Summary(total, ok, error, unreachable, redirect);
			return new JsonReport(summary, details);
		}
		
		private JsonReport(Summary summary, List<Details> details) {
			this.summary = summary;
			this.details = details;
		}
		
		public Summary getSummary() {
			return summary;
		}

		public List<Details> getDetails() {
			return details;
		}

		public static final class Summary {
			
			private final int total;
			private final int ok;
			private final int error;
			private final int unreachable;
			private final int redirect;
			
			private Summary(int total, int ok, int error, int unreachable, int redirect) {
				this.total = total;
				this.ok = ok;
				this.error = error;
				this.unreachable = unreachable;
				this.redirect = redirect;
			}

			public int getTotal() {
				return total;
			}

			public int getOk() {
				return ok;
			}

			public int getError() {
				return error;
			}

			public int getUnreachable() {
				return unreachable;
			}

			public int getRedirect() {
				return redirect;
			}
		}
		
		public static final class Details {
			
			private final int id;
			private final String start;
			private final String end;
			private final int ok;
			private final int error;
			private final int unreachable;
			private final int redirect;
			
			private Details(int id, Date start, Date end, int ok, int error, int unreachable, int redirect) {
				this.id = id;
				this.start = DATE_FORMAT_TL.get().format(start);
				this.end = DATE_FORMAT_TL.get().format(end);
				this.ok = ok;
				this.error = error;
				this.unreachable = unreachable;
				this.redirect = redirect;
			}

			public int getId() {
				return id;
			}

			public String getStart() {
				return start;
			}
			
			public String getEnd() {
				return end;
			}

			public int getOk() {
				return ok;
			}

			public int getError() {
				return error;
			}

			public int getUnreachable() {
				return unreachable;
			}

			public int getRedirect() {
				return redirect;
			}
		}
	}
	
	/**
	 * JSON representation of the details
	 */
	public static final class JsonDetails {
		
		private final int id;
		private final String start;
		private final String end;
		private final List<Link> links;
		
		private JsonDetails(int id, Date start, Date end, List<Link> links) {
			this.id = id;
			this.start = DATE_FORMAT_TL.get().format(start);
			this.end = DATE_FORMAT_TL.get().format(end);
			this.links = links;
		}
		
		public int getId() {
			return id;
		}

		public String getStart() {
			return start;
		}

		public String getEnd() {
			return end;
		}

		public List<Link> getLinks() {
			return links;
		}

		public static final class Link {
			
			private final Status status;
			private final String type;
			private final String location;
			private final String target;
			private final Integer responseCode;
			private final String redirectUrl;
			
			private Link(Status status, String type, String location, String target, 
					Integer responseCode, String redirectUrl) {
				this.status = status;
				this.type = type;
				this.location = location;
				this.target = target;
				this.responseCode = responseCode;
				this.redirectUrl = redirectUrl;
			}
			
			public Status getStatus() {
				return status;
			}

			public String getType() {
				return type;
			}

			public String getLocation() {
				return location;
			}

			public String getTarget() {
				return target;
			}

			public Integer getResponseCode() {
				return responseCode;
			}

			public String getRedirectUrl() {
				return redirectUrl;
			}

			public enum Status {
				OK, ERROR, UNREACHABLE, REDIRECT
			}
		}
	}
}
