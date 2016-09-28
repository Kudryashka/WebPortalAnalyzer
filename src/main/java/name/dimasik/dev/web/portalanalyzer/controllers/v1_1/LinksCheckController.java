package name.dimasik.dev.web.portalanalyzer.controllers.v1_1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@CrossOrigin
public class LinksCheckController implements ExceptionHandledController {

	private static final Logger logger = LoggerFactory.getLogger(LinksCheckController.class);
	
	private static ThreadLocal<DateFormat> DATE_FORMAT_TL = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("HH:mm dd/MM/yyyy");
		}
	};
	
	/**
	 * Request to run check of links on the portal.
	 */
	@PutMapping("/run")
	public ResponseEntity<String> run() {
		logger.info("run()");
		//TODO
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
		//TODO
		return ResponseEntity.ok(new JsonStatus(JsonStatus.Status.FREE));
	}
	
	/**
	 * Get report of check of links on the portal for specified period.
	 */
	@GetMapping("/report/{days}")
	public ResponseEntity<JsonReport> getReport(@PathVariable("days") String daysStr) throws DaysCountFormatException {
		logger.info("report() Days count: " + daysStr);
		
		int days = Parser.parseDaysCount(daysStr);
		//TODO
		return null;
	}
	
	/**
	 * Get details about some concrete check of links on the portal.
	 */
	@GetMapping("/details/{id}")
	public JsonDetails getDetails(@PathVariable("id") String idStr) throws WrongIDFormatException {
		logger.info("details() Check ID: " + idStr);
		
		int id = Parser.parseIntegerID(idStr);
		//TODO
		return null;
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
