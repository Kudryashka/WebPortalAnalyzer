package name.dimasik.dev.web.portalanalyzer.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.dimasik.dev.web.portalanalyzer.checklink.CheckLinkService;

public class CheckLinksOnPortalJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(CheckLinksOnPortalJob.class);
	
	private CheckLinkService service;
	
	public void setService(CheckLinkService service) {
		this.service = service;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			// TODO Auto-generated method stub
			service.checkLinksOnPortal();
			logger.info("Check link service");
		} catch (Exception e) {
			logger.error("Error to process " + CheckLinksOnPortalJob.class.getSimpleName() 
					+ ". Exception message: " + e.getMessage());
		}
	}
}
