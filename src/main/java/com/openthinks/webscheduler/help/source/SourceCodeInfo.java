package com.openthinks.webscheduler.help.source;

public interface SourceCodeInfo {

	/**
	 * validate the source code 
	 * @return boolean
	 */
	boolean validate();

	/**
	 * xxx.java
	 * @return String
	 */
	String getFileName();

	/**
	 * com.openthinks.webscheduler.task.custom.xxxxTask
	 * @return String
	 */
	String getClassFullName();

}