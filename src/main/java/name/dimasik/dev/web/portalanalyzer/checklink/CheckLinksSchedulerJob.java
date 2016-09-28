package name.dimasik.dev.web.portalanalyzer.checklink;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class CheckLinksSchedulerJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(CheckLinksSchedulerJob.class);
	
	private CheckLinkService service;
	
	/**
	 * Using to inject {@link CheckLinkService}
	 */
	public void setService(CheckLinkService service) {
		this.service = service;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {			
			logger.info("Start execution of check link service job");
			service.checkLinksOnPortal();
			logger.info("Execution of check link service job finished");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			logger.error("Error to process " + CheckLinksSchedulerJob.class.getSimpleName() 
					+ ". Exception message: " + e.getMessage() + ". Stacktrace: " + sw.toString());
		}
	}
}
