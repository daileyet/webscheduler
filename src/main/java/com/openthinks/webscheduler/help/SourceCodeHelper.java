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
* @Title: SourceCodeHelper.java 
* @Package com.openthinks.webscheduler.help 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 3, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.help;

import com.openthinks.webscheduler.help.source.SourceCodeInfo;
import com.openthinks.webscheduler.help.source.TaskDefSourceCodeInfo;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class SourceCodeHelper {

	/**
	 * create instance of {@link SourceCodeInfo} by the string of java source code
	 * @param defSourceCode String 
	 * @return SourceCodeInfo
	 */
	public static SourceCodeInfo buildTaskDef(String defSourceCode) {
		return new TaskDefSourceCodeInfo(defSourceCode);
	}

}
