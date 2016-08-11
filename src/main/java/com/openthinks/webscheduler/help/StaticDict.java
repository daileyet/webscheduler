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
* @Title: StaticDict.java 
* @Package com.openthinks.webscheduler.help 
* @author dailey.yet@outlook.com  
* @date Jul 8, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.help;

/**
 * @author dailey.yet@outlook.com
 *
 */
public interface StaticDict {

	public static final String PAGE_ATTRIBUTE_TASK_LIST = "tms";
	public static final String PAGE_ATTRIBUTE_TASK_TYPES = "types";
	public static final String PAGE_ATTRIBUTE_SUPPORT_TASKS = "supportTasks";
	public static final String PAGE_ATTRIBUTE_CUSTOM_TASKS = "customTasks";
	public static final String PAGE_ATTRIBUTE_MAP = "pm";
	public static final String PAGE_ATTRIBUTE_TASK_META = "tm";
	public static final String PAGE_ATTRIBUTE_ERROR_PRE = "error_";
	public static final String PAGE_ATTRIBUTE_ACTIVESIDEBAR = "activeSidebar";
	public static final String PAGE_ATTRIBUTE_TASK_TRIGGERS = "triggers";

	public static final String PAGE_ATTRIBUTE_ERROR_1 = "error_1";
	public static final String PAGE_ATTRIBUTE_ERROR_2 = "error_2";
	public static final String PAGE_ATTRIBUTE_ERROR_3 = "error_3";
	public static final String PAGE_ATTRIBUTE_ERROR_4 = "error_4";
	public static final String PAGE_ATTRIBUTE_ERROR_5 = "error_5";
	public static final String PAGE_ATTRIBUTE_ERROR_6 = "error_6";

	public static final String PAGE_PARAM_TASK_ID = "taskid";
	public static final String PAGE_PARAM_TASK_NAME = "taskname";
	public static final String PAGE_PARAM_TASK_TYPE = "tasktype";
	public static final String PAGE_PARAM_TASK_REF = "taskref";
	public static final String PAGE_PARAM_TASK_TRIGGER = "tasktrigger";
	public static final String PAGE_PARAM_TASK_DEF_CLASS_NAME = "customtasktype";
	public static final String PAGE_PARAM_TASK_TRIGGER_REPEATABLE = "repeatable";
	public static final String PAGE_PARAM_TASK_TRIGGER_REPEATABLE_ENABLE = "true";
	public static final String PAGE_PARAM_TASK_TRIGGER_REPEAT_COUNT = "repeatcount";
	public static final String PAGE_PARAM_TASK_TRIGGER_REPEAT_INTERVAL = "repeatinterval";
	public static final String PAGE_PARAM_TASK_TRIGGER_ENDDATE = "enddate";
	public static final String PAGE_PARAM_TASK_TRIGGER_STARTDATE = "startdate";
	public static final String PAGE_PARAM_TASK_TRIGGER_CRON_EXPR = "cronexpr";

	public static final String STORE_DB = "webscheduler.odb";

	public static final String CUSTOM_TASK_DEF_PACKAGE = "com.openthinks.webscheduler.task.custom";
	//public static final String DEFAULT_DATE_FORMAT_STYLE = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATE_FORMAT_STYLE = "yyyy-MM-dd HH:mm";
	public static final String PAGE_PARAM_TASK_DEF_CODE = "taskdef";
	public static final String DISK_STORE_TASKDEF = "taskdef_disk";
	public static final String MEMORY_STORE_TASKDEF = "taskdef_memory";
	public static final String DISK_STORE_TASK = "task_disk";
	public static final String MEMORY_STORE_TASK = "task_memory";
	public static final int NO_REPEAT_TRIGGER = -1;
	public static final int FOREVER_REPEAT_TRIGGER = Integer.MAX_VALUE;
	public static final int DEFAULT_REPEAT_INTERVAL = 0; //3 second

	public static final String TRIGGER_SUFFIX = "-Trigger";
	public static final String DEFAULT_TASK_GROUP_NAME = "default_group";

}
