package name.dimasik.dev.web.portalanalyzer.schedule;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import name.dimasik.dev.web.portalanalyzer.checklink.CheckLinkService;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;

@Service
public class ScheduleService {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
	
	private final Object schedulerMutex = new Object();
	private Scheduler scheduler;
	private CheckLinkService checkLinkService;
	
	@Autowired
	public void setCheckLinkService(CheckLinkService checkLinkService) {
		this.checkLinkService = checkLinkService;
	}

	@EventListener
	public void handleSchedulerInitialization(ContextRefreshedEvent event) throws SchedulerException {
		synchronized (schedulerMutex) {
			if (scheduler == null || scheduler.isShutdown()) {
				try {
					scheduler = StdSchedulerFactory.getDefaultScheduler();
					scheduler.start();
					logger.info("Scheduler initialized.");
				} catch (SchedulerException e) {
					logger.error("Fail to initialize scheduler. Exception message: " + e.getMessage());
				}
				
				//TODO initialize scheduler jobs
				scheduleCheckLinksOnPortalJob(scheduler);
			}
		}
	}
	
	@EventListener
	public void handleSchedulerShutdown(ContextClosedEvent event) {
		synchronized (schedulerMutex) {
			if (scheduler != null) {
				try {
					scheduler.shutdown();
					logger.info("Scheduler destroyed.");
				} catch (SchedulerException e) {
					logger.error("Fail to destroy scheduler. Exception message: " + e.getMessage());
				}
			}
		}
	}
	
	private void scheduleCheckLinksOnPortalJob(Scheduler scheduler) {
		JobDataMap dataMap = new JobDataMap();
		dataMap.put("service", checkLinkService); 
		
		JobDetail job = newJob(CheckLinksOnPortalJob.class)
				.withIdentity("checkLinksOnPortalJob", "checkLinks")
				.usingJobData(dataMap)
				.build();
		
		Trigger trigger = newTrigger()
				.withIdentity("checkLinksOnPortalTrigger", "checkLinks")
				.startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever())
				.build();
		
		try {
			scheduler.scheduleJob(job, trigger);
			logger.info("Scheduled job " + CheckLinksOnPortalJob.class.getSimpleName());
		} catch (SchedulerException e) {
			logger.error("Fail to schedule " + CheckLinksOnPortalJob.class.getSimpleName() + " job. Exception message: " + e.getMessage());
		}
	}
}
