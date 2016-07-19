package com.openthinks.webscheduler.task.support;

import java.io.IOException;

import openthinks.others.safaribook.SafariBookLaunch;
import openthinks.others.webpages.exception.LaunchFailedException;

import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.model.TaskMetaData;
import com.openthinks.webscheduler.model.task.ITaskRef;
import com.openthinks.webscheduler.model.task.TaskState;
import com.openthinks.webscheduler.task.TaskContext;

public class SafaribooksonlineGetterTask implements SupportTask {

	@Override
	public void execute(TaskContext context) {
		ProcessLogger.debug(getClass() + ":Job start...");
		SafaribooksonlineGetterRef bookConfigure = new SafaribooksonlineGetterRef();
		TaskMetaData taskMetaData = getTaskMetaData(context).get();
		try {
			bookConfigure.readString(taskMetaData.getTaskRefContent());
			SafariBookLaunch bookLaunch = new SafariBookLaunch(bookConfigure);
			taskMetaData.setTaskState(TaskState.RUNNING);
			bookLaunch.start();
			taskMetaData.setTaskState(TaskState.COMPLETE);
		} catch (IOException e) {
			taskMetaData.setTaskState(TaskState.INTERRUPT);
			ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e.getMessage());
		} catch (LaunchFailedException e) {
			taskMetaData.setTaskState(TaskState.INTERRUPT);
			ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e.getMessage());
		} catch (Exception e) {
			taskMetaData.setTaskState(TaskState.INTERRUPT);
			ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e.getMessage());
		}

	}

	@Override
	public String getDescription() {
		return "Safari book download task";
	}

	@Override
	public Class<? extends ITaskRef> getTaskRef() {
		return SafaribooksonlineGetterRef.class;
	}

}
