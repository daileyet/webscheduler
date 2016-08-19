package com.openthinks.webscheduler;

import com.openthinks.libs.utilities.version.AppVersion;
import com.openthinks.libs.utilities.version.VersionCenter;
import com.openthinks.webscheduler.task.support.AdvanceDownloadFileTask;
import com.openthinks.webscheduler.task.support.EchoTask;
import com.openthinks.webscheduler.task.support.SafaribooksonlineGetterTask;
import com.openthinks.webscheduler.task.support.SimpleDownloadFileTask;

@AppVersion("0.1")
public class Versions extends VersionCenter {

	/**
	 * @base
	 * <ul>
	 * 	<li> UI
	 * 	<ol>
	 * 		<li> Dashboard; Chart.js
	 * 		<li> Task; task management, task definition
	 * 		<li> Help;
	 * 		<li> TODO User; security management, user management 
	 * 		<li> TODO Profile;
	 * 	</ol>
	 *  <li> Support Task(build-in)
	 *  <ol>
	 *  	<li> {@link SafaribooksonlineGetterTask}
	 *  	<li> {@link SimpleDownloadFileTask}
	 *  	<li> {@link EchoTask}
	 *  	<li> TODO {@link AdvanceDownloadFileTask}
	 *  </ol>
	 * </ul>
	 */
	String v_0_1;
}
