package com.openthinks.webscheduler.task.support;

import openthinks.others.safaribook.SafariBookConfigure;
import openthinks.others.safaribook.SafariBookLaunch;
import openthinks.others.webpages.exception.LaunchFailedException;

import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.task.Task;
import com.openthinks.webscheduler.task.TaskContext;

public class SafaribooksonlineGetterTask implements Task {

	@Override
	public void execute(TaskContext context) {

		SafariBookConfigure bookConfigure = (SafariBookConfigure) context.get(TASK_REF);
		ProcessLogger.debug(getClass() + ":Job start...");
		SafariBookLaunch bookLaunch = new SafariBookLaunch(bookConfigure);
		try {
			bookLaunch.start();
		} catch (LaunchFailedException e) {
			ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e.getMessage());
		}

	}

}
