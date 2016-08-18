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
* @Title: SimpleDownloadFileTask.java 
* @Package com.openthinks.webscheduler.task.support 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 18, 2016
* @version V1.0   
*/
package com.openthinks.webscheduler.task.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Optional;
import java.util.UUID;

import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.model.task.DefaultTaskRef;
import com.openthinks.webscheduler.model.task.ITaskRef;
import com.openthinks.webscheduler.task.TaskContext;
import com.openthinks.webscheduler.task.TaskDefinitionDescriber;
import com.openthinks.webscheduler.task.TaskRefDefinitionDescriber;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class SimpleDownloadFileTask implements SupportTaskDefinition {

	@Override
	public void execute(TaskContext context) {
		ProcessLogger.debug("Start download...");
		TaskRunTimeData taskRunTimeData = getTaskRunTimeData(context).get();
		ITaskRef taskRef = new DefaultTaskRef();
		FileOutputStream fos = null;
		try {
			taskRef.readString(taskRunTimeData.getTaskRefContent());
			checkTaskRef(taskRef);
			Optional<String> optUrl = taskRef.getProp("download-url");
			//"https://github.com/chartjs/Chart.js/releases/download/v2.2.1/Chart.bundle.js"
			URL website = new URL(optUrl.get());
			Optional<String> optProxyHost = taskRef.getProp("proxy-host");
			URLConnection urlConnection = null;
			if (optProxyHost.isPresent()) {
				int port = 80;
				Optional<String> optProxyPort = taskRef.getProp("proxy-port");
				if (optProxyPort.isPresent()) {
					try {
						port = Integer.valueOf(optProxyPort.get());
					} catch (NumberFormatException e) {
						ProcessLogger.warn(
								"Exception occur in" + getClass().getName() + ",and will use default proxy port:80", e);
					}
				}
				Proxy proxy = new Proxy(Type.HTTP, InetSocketAddress.createUnresolved(optProxyHost.get(), port));
				urlConnection = website.openConnection(proxy);
			} else {
				urlConnection = website.openConnection();
			}
			Optional<String> optSavedir = taskRef.getProp("save-dir");
			Optional<String> optFilename = taskRef.getProp("file-name");
			if (!optFilename.isPresent()) {
				optFilename = generateFileName(taskRef);
				taskRef.setProperty("file-name", optFilename.get());
			}
			ReadableByteChannel rbc = Channels.newChannel(urlConnection.getInputStream());
			//"Chart.bundle.js"
			File file = new File(optSavedir.get(), optFilename.get());
			fos = new FileOutputStream(file);
			ProcessLogger.debug("Start save...");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			ProcessLogger.debug("Finish download!");
		} catch (IOException e) {
			ProcessLogger.error(this.getClass().getName(), e);
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					ProcessLogger.error(this.getClass().getName(), e);
				}
		}

	}

	@Override
	public TaskRefDefinitionDescriber getTaskRefDescriber() {
		TaskRefDefinitionDescriber describer = DefaultTaskRef.getTaskRefDescriber();
		describer.setRequired(true);
		preparedRefDescription(describer);
		return describer;
	}

	protected void preparedRefDescription(TaskRefDefinitionDescriber describer) {
		describer.push("#[required]download file URL");
		describer.push("download-url=https://xxxx/xx/xxx.zip");
		describer.push("#[option]proxy host if present");
		describer.push("proxy-host=http://example.proxy.com");
		describer.push("#[option]proxy host port");
		describer.push("proxy-port=80");
		describer.push("#[required]download save directory");
		describer.push("save-dir=to/path/save/dir");
		describer.push("#[option]download file name");
		describer.push("file-name=xxx.zip");
	}

	@Override
	public TaskDefinitionDescriber getTaskDescriber() {
		return TaskDefinitionDescriber.build(getClass()).push("Simple file download task.");
	}

	protected Optional<String> generateFileName(ITaskRef taskRef) {
		String fileName = null;
		Optional<String> optUrl = taskRef.getProp("download-url");
		if (optUrl.isPresent()) {
			String url = optUrl.get();
			int index = url.lastIndexOf("/");
			fileName = url.substring(index + 1);
		}
		if (fileName != null && !"".equals(fileName.trim())) {
			return Optional.of(fileName);
		} else {
			return Optional.of(UUID.fromString(optUrl.get()).toString());
		}
	}

	protected void checkTaskRef(ITaskRef taskRef) {
		//TODO
	}

}
