package name.dimasik.dev.web.portalanalyzer.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.dimasik.dev.web.portalanalyzer.checklink.CheckLinkService;

/**
 * 
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class CheckLinksOnPortalJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(CheckLinksOnPortalJob.class);
	
	private CheckLinkService service;
	
	/**
	 * 
	 * @param service
	 */
	public void setService(CheckLinkService service) {
		this.service = service;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			service.checkLinksOnPortal();
			// TODO process result
			logger.info("Check link service");
		} catch (Exception e) {
			logger.error("Error to process " + CheckLinksOnPortalJob.class.getSimpleName() 
					+ ". Exception message: " + e.getMessage());
		}
	}
}
