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
* @Title: StaticChecker.java 
* @Package com.openthinks.webscheduler.help 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 13, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.help;

import com.openthinks.libs.utilities.Checker;
import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.model.task.ExecutionResult;
import com.openthinks.webscheduler.model.task.ITaskTrigger;
import com.openthinks.webscheduler.model.task.SupportedTrigger;
import com.openthinks.webscheduler.model.task.TaskAction;
import com.openthinks.webscheduler.model.task.TaskState;
import com.openthinks.webscheduler.model.task.trigger.CronTaskTrigger;
import com.openthinks.webscheduler.model.task.trigger.SimpleTaskTrigger;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class StaticChecker {

	public static boolean isRefXML(String content) {
		boolean is = content.indexOf("<?xml") != -1;
		if (!is)
			return false;
		is = content.indexOf("<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">") != -1;
		if (!is)
			return false;
		is = content.indexOf("<properties>") != -1;
		if (!is)
			return false;
		return true;
	}

	public static boolean isCompleteWith(TaskRunTimeData taskRunTimeData, boolean isSuccess) {
		if (taskRunTimeData != null && taskRunTimeData.getTaskState() == TaskState.COMPLETE) {
			ExecutionResult taskResult = taskRunTimeData.getLastTaskResult();
			if (taskResult != null && taskResult.isSuccess() != null && taskResult.isSuccess() == isSuccess) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAvaiableWith(TaskRunTimeData taskRunTimeData, TaskAction taskAction) {
		boolean isAvaiable = false;
		if (taskRunTimeData == null || taskAction == null) {
			return isAvaiable;
		}
		switch (taskAction) {
		case Schedule:
			isAvaiable = (taskRunTimeData.getTaskState() != TaskState.RUNNING);
			break;
		case Stop:
			isAvaiable = (taskRunTimeData.getTaskState() == TaskState.RUNNING);
			//disable this action always
			isAvaiable = false;
			break;
		case Edit:
			isAvaiable = (taskRunTimeData.getTaskState() != TaskState.RUNNING);
			break;
		case Remove:
			isAvaiable = (taskRunTimeData.getTaskState() != TaskState.RUNNING
					&& taskRunTimeData.getTaskState() != TaskState.INVALID);
			break;
		}
		return isAvaiable;
	}

	public static boolean isAvaiableWith(TaskRunTimeData taskRunTimeData, String taskActionStr) {
		boolean isAvaiable = false;
		TaskAction taskAction = TaskAction.valueOf(taskActionStr);
		isAvaiable = isAvaiableWith(taskRunTimeData, taskAction);
		return isAvaiable;
	}

	public static boolean isSimpleTaskTrigger(TaskRunTimeData taskRunTimeData) {
		Checker.require(taskRunTimeData).notNull();
		ITaskTrigger taskTrigger = taskRunTimeData.getTaskTrigger();
		Checker.require(taskTrigger).notNull();
		return taskTrigger instanceof SimpleTaskTrigger;
	}

	public static boolean isSimple4FixDate(TaskRunTimeData taskRunTimeData) {
		Checker.require(taskRunTimeData).notNull();
		ITaskTrigger taskTrigger = taskRunTimeData.getTaskTrigger();
		Checker.require(taskTrigger).notNull();
		return taskTrigger.getTriggerType() == SupportedTrigger.START_FIX_DATE;
	}

	public static boolean isSimple4Repeatable(TaskRunTimeData taskRunTimeData) {
		Checker.require(taskRunTimeData).notNull();
		ITaskTrigger taskTrigger = taskRunTimeData.getTaskTrigger();
		Checker.require(taskTrigger).notNull();
		if (taskTrigger instanceof SimpleTaskTrigger) {
			return ((SimpleTaskTrigger) taskTrigger).isRepeatable();
		}
		return false;
	}

	//isCronTaskTrigger
	public static boolean isCronTaskTrigger(TaskRunTimeData taskRunTimeData) {
		Checker.require(taskRunTimeData).notNull();
		ITaskTrigger taskTrigger = taskRunTimeData.getTaskTrigger();
		Checker.require(taskTrigger).notNull();
		return taskTrigger instanceof CronTaskTrigger;
	}

	//isRepeatForever
	public static boolean isRepeatForever(TaskRunTimeData taskRunTimeData) {
		Checker.require(taskRunTimeData).notNull();
		ITaskTrigger taskTrigger = taskRunTimeData.getTaskTrigger();
		Checker.require(taskTrigger).notNull();
		if (taskTrigger instanceof SimpleTaskTrigger) {
			return ((SimpleTaskTrigger) taskTrigger).isRepeatForever();
		}
		return false;
	}

	//getRepeatInterval
	public static String getRepeatInterval(TaskRunTimeData taskRunTimeData) {
		Checker.require(taskRunTimeData).notNull();
		ITaskTrigger taskTrigger = taskRunTimeData.getTaskTrigger();
		Checker.require(taskTrigger).notNull();
		if (taskTrigger instanceof SimpleTaskTrigger) {
			return ((SimpleTaskTrigger) taskTrigger).getIntervalInSeconds() + "";
		}
		return "";
	}

	//getRepeatCount
	public static String getRepeatCount(TaskRunTimeData taskRunTimeData) {
		Checker.require(taskRunTimeData).notNull();
		ITaskTrigger taskTrigger = taskRunTimeData.getTaskTrigger();
		Checker.require(taskTrigger).notNull();
		if (taskTrigger instanceof SimpleTaskTrigger) {
			return ((SimpleTaskTrigger) taskTrigger).getRepeatCount() + "";
		}
		return "";
	}

	//getStartDate
	public static String getStartDate(TaskRunTimeData taskRunTimeData) {
		Checker.require(taskRunTimeData).notNull();
		ITaskTrigger taskTrigger = taskRunTimeData.getTaskTrigger();
		Checker.require(taskTrigger).notNull();
		if (taskTrigger instanceof SimpleTaskTrigger) {
			return ((SimpleTaskTrigger) taskTrigger).getStartTimeString();
		}
		return "";
	}

	//getEndDate
	public static String getEndDate(TaskRunTimeData taskRunTimeData) {
		Checker.require(taskRunTimeData).notNull();
		ITaskTrigger taskTrigger = taskRunTimeData.getTaskTrigger();
		Checker.require(taskTrigger).notNull();
		if (taskTrigger instanceof SimpleTaskTrigger) {
			return ((SimpleTaskTrigger) taskTrigger).getEndTimeString();
		}
		return "";
	}

	//getCronExpr
	public static String getCronExpr(TaskRunTimeData taskRunTimeData) {
		Checker.require(taskRunTimeData).notNull();
		ITaskTrigger taskTrigger = taskRunTimeData.getTaskTrigger();
		Checker.require(taskTrigger).notNull();
		if (taskTrigger instanceof CronTaskTrigger) {
			return ((CronTaskTrigger) taskTrigger).getCronExpression();
		}
		return "";
	}
}
