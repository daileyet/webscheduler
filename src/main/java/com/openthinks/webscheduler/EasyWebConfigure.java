package com.openthinks.webscheduler;

import org.quartz.SchedulerException;

import com.openthinks.easyweb.annotation.configure.EasyConfigure;
import com.openthinks.easyweb.annotation.configure.RequestSuffixs;
import com.openthinks.easyweb.annotation.configure.ScanPackages;
import com.openthinks.easyweb.context.Bootstrap;
import com.openthinks.libs.sql.dhibernate.support.SessionFactory;
import com.openthinks.libs.sql.lang.Configurator;
import com.openthinks.libs.sql.lang.ConfiguratorFactory;
import com.openthinks.libs.utilities.logger.ProcessLogger;

@EasyConfigure
@ScanPackages({ "com.openthinks.webscheduler" })
@RequestSuffixs(".do,.htm")
public class EasyWebConfigure implements Bootstrap {

	@Override
	public void cleanUp() {
	}

	@Override
	public void initial() {
		ProcessLogger.currentLevel = ProcessLogger.PLLevel.DEBUG;

		Configurator configuration = ConfiguratorFactory.getInstance(getClass().getResourceAsStream(
				"/dbconfig.properties"));
		SessionFactory.setDefaultConfigurator(configuration);

		ProcessLogger.info("Start WebScheduler...");
		try {
			SchedulerManagement.get().start();
		} catch (SchedulerException e) {
			ProcessLogger.fatal(e.getMessage());
		}

	}
}
