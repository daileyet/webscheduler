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
* @Title: TaskInterruptException.java 
* @Package com.openthinks.webscheduler.task 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 27, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.task;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class TaskInterruptException extends RuntimeException {

	private static final long serialVersionUID = 234823812303024249L;

	public TaskInterruptException() {
		super();
	}

	public TaskInterruptException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TaskInterruptException(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskInterruptException(String message) {
		super(message);
	}

	public TaskInterruptException(Throwable cause) {
		super(cause);
	}

}
