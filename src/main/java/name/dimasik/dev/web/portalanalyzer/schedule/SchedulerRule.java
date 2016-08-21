package name.dimasik.dev.web.portalanalyzer.schedule;

import com.fasterxml.jackson.annotation.JsonView;

import name.dimasik.dev.web.portalanalyzer.controllers.v1_0.LinksCheckResponseView;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class SchedulerRule {
	
	@JsonView(LinksCheckResponseView.class)
	private int id;
	@JsonView(LinksCheckResponseView.class)
	private boolean isActive;

	@JsonView(LinksCheckResponseView.class)
	private int minutes;
	@JsonView(LinksCheckResponseView.class)
	private int hours;
	@JsonView(LinksCheckResponseView.class)
	private int dayOfWeek;
	@JsonView(LinksCheckResponseView.class)
	private int dayOfMonth;
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getMinutes() {
		return minutes;
	}
	
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	public int getHours() {
		return hours;
	}
	
	public void setHours(int hours) {
		this.hours = hours;
	}
	
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public int getDayOfMonth() {
		return dayOfMonth;
	}
	
	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	
	public boolean isValid() {
		return minutes >= 0 && minutes < 60 && hours >=0 && hours < 24 && dayOfWeek >= 0 && dayOfWeek <= 7 && dayOfMonth >= 0 && dayOfMonth <= 31;
	}
}
