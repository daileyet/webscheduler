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
* @Title: SupportedTrigger.java 
* @Package com.openthinks.webscheduler.task 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 9, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.model.task;

/**
 * @author dailey.yet@outlook.com
 *
 */
public enum SupportedTrigger {
	START_NOW("Start now", "simple1-trigger"), START_FIX_DATE("Start at one time",
			"simple2-trigger"), CRON("Cron expression", "cron-trigger");

	private String display, tag;

	private SupportedTrigger(String display, String tag) {
		this.display = display;
		this.tag = tag;
	}

	public String getDisplay() {
		return display;
	}

	public String getName() {
		return this.name();
	}

	public String getTag() {
		return tag;
	}

	@Override
	public String toString() {
		return getDisplay();
	}
}
