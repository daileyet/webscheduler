package com.openthinks.webscheduler.model.task;

import openthinks.others.safaribook.SafariBookConfigure;
import openthinks.others.safaribook.SafariBookLaunch;
import openthinks.others.webpages.exception.LaunchFailedException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;

public class SafaribooksonlineGetterTask implements Job {

	public static final String CONFIGURE = "SafariBookConfigure";

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//		context.getJobDetail().getJobDataMap().get(CONFIGURE)

		SafariBookConfigure bookConfigure = (SafariBookConfigure) context.getMergedJobDataMap().get(CONFIGURE);
		ProcessLogger.debug(getClass() + ":Job start...");
		SafariBookLaunch bookLaunch = new SafariBookLaunch(bookConfigure);
		try {
			bookLaunch.start();
		} catch (LaunchFailedException e) {
			ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e.getMessage());
			throw new JobExecutionException(e);
		}
	}
}
