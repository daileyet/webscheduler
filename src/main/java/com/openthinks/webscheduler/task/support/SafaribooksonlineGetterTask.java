package com.openthinks.webscheduler.task.support;

import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.others.safaribook.SafariBookLaunch;
import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.task.TaskContext;
import com.openthinks.webscheduler.task.TaskDefinitionDescriber;
import com.openthinks.webscheduler.task.TaskInterruptException;
import com.openthinks.webscheduler.task.TaskRefDefinitionDescriber;

public class SafaribooksonlineGetterTask implements SupportTaskDefinition {

	@Override
	public void execute(TaskContext context) {
		ProcessLogger.debug(getClass() + ":Job start...");
		SafaribooksonlineGetterRef bookConfigure = new SafaribooksonlineGetterRef();
		TaskRunTimeData taskRunTimeData = getTaskRunTimeData(context).get();
		try {
			bookConfigure.readString(taskRunTimeData.getTaskRefContent());
			ProcessLogger.debug(bookConfigure.toString());
			SafariBookLaunch bookLaunch = new SafariBookLaunch(bookConfigure);
			bookLaunch.start();
		} catch (Exception e) {
			ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e.getMessage());
			throw new TaskInterruptException(e);
		}

	}

	@Override
	public TaskRefDefinitionDescriber getTaskRefDescriber() {
		TaskRefDefinitionDescriber describer = new TaskRefDefinitionDescriber(SafaribooksonlineGetterRef.class);
		describer.setRequired(true);
		preparedRefDescription(describer);
		return describer;
	}

	protected void preparedRefDescription(TaskRefDefinitionDescriber describer) {
		describer.push("#[option]Browser client: FF31; FF38; IE8; IE11; CHROME");
		describer.push("browser-version=FF38");
		describer.push("#[option]proxy host if present");
		describer.push("proxy-host=http://example.proxy.com");
		describer.push("#[option]proxy host port");
		describer.push("proxy-port=80");
		describer.push("#[required]download save directory");
		describer.push("save-dir=to/path/save/dir");
		describer.push("#[required]identity the authorized for the download pages");
		describer.push("need-login=true");
		describer.push("#-------------------------------------------------");
		describer.push("#required when need login is true");
		describer.push("#[required]the login page url");
		describer.push("login-url=https://www.safaribooksonline.com/accounts/login/");
		describer.push("#[required]the login page form css selector");
		describer.push("login-form-selector=form");
		describer.push("#[option]the login page form index");
		describer.push("login-form-index=0");
		describer.push("#[required]the login page form user name input name");
		describer.push("login-form-username-input-name=email");
		describer.push("#[required]the login page form user name value");
		describer.push("login-form-username-input-value=your email");
		describer.push("#[required]the login page form user pass input name");
		describer.push("login-form-password-input-name=password1");
		describer.push("#[required]the login page form user pass value");
		describer.push("login-form-password-input-value=your password");
		describer.push("#[required]the login page form submit button name");
		describer.push("login-form-submit-name=login");
		describer.push("#-------------------------------------------------");
		describer.push("#optional when the first page url is configured");
		describer.push(
				"#The catalog page url,just like: http://techbus.safaribooksonline.com/book/databases/oracle-pl-sql/9780133798548");
		describer.push("pages-catalog-url=the book catalog page url");
		describer.push(
				"#The css selector for each download page anchor on catalog page, just like: div.catalog_container a[href*='9781784397203'] ");
		describer.push("catalog-pagelinks-selector=div.catalog_container a[href*='book number']");
		describer.push("#optional when the catalog page url is configured");
		describer.push(
				"#The first page url of the book, just like: http://techbus.safaribooksonline.com/book/programming/android/9781784397203/android-studio-essentials/index_html");
		describer.push("pages-first-url=the book first page url");
		describer.push("#The css selector for next chain page anchor on each page");
		describer.push("pages-next-anchor-selector=a.next[title*='Next (Key: n)']");
		describer.push("");
		describer.push("#-------------------------------------------------");
		describer.push("#show message in CMD, the log level include: DEBUG,INFO,WARN,ERROR,FATAL");
		describer.push("logger-level=INFO");

	}

	@Override
	public TaskDefinitionDescriber getTaskDescriber() {
		return TaskDefinitionDescriber.build(SafaribooksonlineGetterTask.class).push("Safari book download task.");
	}

}
