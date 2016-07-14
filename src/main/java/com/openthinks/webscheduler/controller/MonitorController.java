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
* @Title: MonitorController.java 
* @Package com.openthinks.webscheduler.controller 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Jul 14, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.controller;

import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.annotation.process.objects.WebContainer;
import com.openthinks.easyweb.context.RequestSuffix;
import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.easyweb.context.handler.WebAttributers;
import com.openthinks.easyweb.context.handler.WebAttributers.WebScope;
import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * @author dailey.yet@outlook.com
 *
 */
@Controller("/monitors")
public class MonitorController {

	@Mapping("/index")
	public String index(WebAttributers was) {
		WebContainer webContainer = WebContexts.get().getWebContainer();
		RequestSuffix requestSuffix = WebContexts.get().getWebConfigure().getRequestSuffix();
		was.addAttribute("webContainer", webContainer, WebScope.REQUEST);
		was.addAttribute("requestSuffixs", requestSuffix.options(), WebScope.REQUEST);
		return "WEB-INF/monitor.jsp";
	}

	@Mapping("/reload")
	public String reload(WebAttributers was) {
		try {
			WebContexts.get().getWebProcesser().reload();
		} catch (ClassNotFoundException e) {
			ProcessLogger.error(">>>>>>>>>>>>>>>>>>> Reload error:" + e.getMessage());
		}
		WebContexts.get().getWebProcesser().process();
		return index(was);
	}

}
