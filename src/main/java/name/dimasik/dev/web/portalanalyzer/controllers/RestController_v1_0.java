package name.dimasik.dev.web.portalanalyzer.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * TODO delete this controller
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
@RestController
@RequestMapping("/api/v1.0/")
@Deprecated
public class RestController_v1_0 {
	
	public static class SchedulerRule {
		
		@JsonView(View.class)
		private int id;
		@JsonView(View.class)
		private String cronExpression;
		@JsonView(View.class)
		private boolean active;
		
		/**
		 * 
		 */
		public SchedulerRule() {
		}
		
		/**
		 * 
		 * @param id
		 * @param cronExpression
		 * @param active
		 */
		public SchedulerRule(int id, String cronExpression, boolean active) {
			this.id = id;
			this.cronExpression = cronExpression;
			this.active = active;
		}
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getCronExpression() {
			return cronExpression;
		}

		public void setCronExpression(String cronExpression) {
			this.cronExpression = cronExpression;
		}

		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}
		
		public interface View {};
	}
}
