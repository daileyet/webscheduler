package com.openthinks.webscheduler;

import org.quartz.SchedulerException;

import com.openthinks.easyweb.annotation.configure.EasyConfigure;
import com.openthinks.easyweb.annotation.configure.RequestSuffixs;
import com.openthinks.easyweb.annotation.configure.ScanPackages;
import com.openthinks.easyweb.context.Bootstrap;
import com.openthinks.easyweb.context.WebContexts;
import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.webscheduler.dao.impl.mapdb.MapDBHelper;
import com.openthinks.webscheduler.service.SchedulerService;

@EasyConfigure
@ScanPackages({ "com.openthinks.webscheduler" })
@RequestSuffixs(".do,.htm")
public class EasyWebConfigure implements Bootstrap {
	private SchedulerService schedulerService;

	@Override
	public void cleanUp() {
		ProcessLogger.info("Stop WebScheduler...");
		if (schedulerService != null) {
			try {
				schedulerService.stop();
			} catch (SchedulerException e) {
				ProcessLogger.fatal(CommonUtilities.getCurrentInvokerMethod(), e.getMessage());
			}
		}
		MapDBHelper.destroy();
	}

	@Override
	public void initial() {
		ProcessLogger.currentLevel = ProcessLogger.PLLevel.DEBUG;
		schedulerService = WebContexts.get().lookup(SchedulerService.class);
		ProcessLogger.info("Start WebScheduler...");
		try {
			schedulerService.start();
		} catch (SchedulerException e) {
			ProcessLogger.fatal(CommonUtilities.getCurrentInvokerMethod(), e.getMessage());
		}
	}
}
