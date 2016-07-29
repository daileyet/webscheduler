package com.openthinks.webscheduler.model.task;

/**
 * Task support state 
 * @author dailey.yet@outlook.com
 *
 */
public enum TaskState {
	UN_SCHEDULE("Unschedule"), SCHEDULED("Scheduled"), RUNNING("Running"), COMPLETE("Completed"), INTERRUPT(
			"Interrupted"), INVALID("Invalid");

	private String key;
	private String display;

	private TaskState(String display) {
		this.key = this.toString();
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}

	public String getKey() {
		return key;
	}
}
