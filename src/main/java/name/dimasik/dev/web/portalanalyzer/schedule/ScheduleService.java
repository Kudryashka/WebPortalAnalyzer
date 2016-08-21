package name.dimasik.dev.web.portalanalyzer.schedule;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import name.dimasik.dev.web.portalanalyzer.checklink.CheckLinksSchedulerJob;


import java.util.List;

@Service
public class ScheduleService {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
	
	private final Object schedulerMutex = new Object();
	private Scheduler scheduler;

	/**
	 * <b>Don't call this manually!</b>
	 * Used to handle scheduler initialization.
	 */
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
				//initialize jobs
			}
		}
	}
	
	/**
	 * <b>Don't call this manually!</b>
	 * Used to handle scheduler destroying.
	 */
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
	
	/**
	 * 
	 * @param jobClass
	 * @return
	 */
	public boolean isJobExecuting(Class<? extends Job> jobClass) {
		boolean result = false;
		try {
			List<JobExecutionContext> executionContexts = null;
			synchronized (schedulerMutex) {
				executionContexts = scheduler.getCurrentlyExecutingJobs();
			}
			for (JobExecutionContext context : executionContexts) {
				if (context.getJobInstance().getClass() == jobClass) {
					result = true;
					break;
				}
			}
		} catch (SchedulerException e) {
			logger.error("Job execution status retrieve fail. Exception message: " + e.getMessage());
		}
		return result;
	}
	
	/**
	 * TODO
	 * @param job
	 * @param trigger
	 */
	public boolean scheduleJob(JobDetail job, Trigger trigger) {
		boolean success = true;
		try {
			synchronized (schedulerMutex) {
				if (scheduler != null) {
					scheduler.scheduleJob(job, trigger);
				}
			}
			logger.info("Scheduled job " + job.getKey());
		} catch (SchedulerException e) {
			success = false;
			logger.error("Fail to schedule " + CheckLinksSchedulerJob.class.getSimpleName() + " job. Exception message: " + e.getMessage());
		}
		return success;
	}
}
