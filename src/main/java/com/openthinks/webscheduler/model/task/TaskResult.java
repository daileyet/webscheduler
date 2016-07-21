/**   
 *  Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
* @Title: TaskExecuteResult.java 
* @Package com.openthinks.webscheduler.model.task 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 20, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model.task;

import java.util.Date;

import com.openthinks.webscheduler.help.StaticUtils;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class TaskResult {
	private Boolean isSuccess;
	private Date startTime;
	private Date endTime;
	private String logContent;

	public TaskResult() {

	}

	public Boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
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

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.isSuccess() != null) {
			sb.append("Execution: ");
			sb.append(this.isSuccess ? "Success" : "Failed");
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
		if (this.getLogContent() != null) {
			sb.append("Log Track: ");
			sb.append(this.getLogContent());
			sb.append("\r\n");
		}
		return sb.toString();
	}

}
