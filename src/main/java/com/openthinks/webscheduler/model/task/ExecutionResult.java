package com.openthinks.webscheduler.model.task;

import java.io.Serializable;
import java.util.Date;

import com.openthinks.webscheduler.help.StaticUtils;

public class ExecutionResult implements Serializable {

	private static final long serialVersionUID = 840962776805761590L;
	private Boolean success;
	private Date startTime;
	private Date endTime;
	protected String logContent;

	public ExecutionResult() {
		super();
	}

	public ExecutionResult start() {
		setStartTime(new Date());
		return this;
	}

	public ExecutionResult end() {
		setEndTime(new Date());
		return this;
	}

	public Boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean isSuccess) {
		this.success = isSuccess;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date executeTime) {
		this.startTime = executeTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getLogContent() {
		return logContent;
	}

	protected void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public void track(String log) {
		if (log != null) {
			if (this.logContent == null) {
				this.logContent = "";
			}
			this.logContent += log;
			this.logContent += "\r\n";
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.isSuccess() != null) {
			sb.append("Execution: ");
			sb.append(this.success ? "Success" : "Failed");
			sb.append("\r\n");
		}
		if (this.getStartTime() != null) {
			sb.append("Start Time: ");
			sb.append(StaticUtils.formatDate(getStartTime()));
			sb.append("\r\n");
		}
		if (this.getEndTime() != null) {
			sb.append("End Time: ");
			sb.append(StaticUtils.formatDate(getEndTime()));
			sb.append("\r\n");
		}
		if (this.getLogContent() != null && this.getLogContent().length() > 0) {
			sb.append("Log Track: ");
			sb.append(this.getLogContent());
			sb.append("\r\n");
		}
		return sb.toString();
	}

	public void clear() {
		this.logContent = "";
		this.success = null;
		this.startTime = null;
		this.endTime = null;
	}

}