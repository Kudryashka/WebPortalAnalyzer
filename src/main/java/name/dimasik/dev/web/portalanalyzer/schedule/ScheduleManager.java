package name.dimasik.dev.web.portalanalyzer.schedule;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ScheduleManager {

	@EventListener
	public void handleSchedulerLifecicle(ContextRefreshedEvent event) {
		//TODO initialize scheduler
		//TODO check if context already initialized
	}
	
	@EventListener
	public void handleSchedulerLifecicle4(ContextClosedEvent event) {
		//TODO destroy scheduler
	}
}
