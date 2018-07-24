/**
 * 
 */
package com.openthinks.webscheduler.task.support;

import java.util.Optional;

import com.openthinks.libs.utilities.Checker;
import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.others.safaribook.SafariBookLaunch;
import com.openthinks.webscheduler.help.StaticUtils;
import com.openthinks.webscheduler.model.TaskRunTimeData;
import com.openthinks.webscheduler.task.TaskContext;
import com.openthinks.webscheduler.task.TaskDefinitionDescriber;
import com.openthinks.webscheduler.task.TaskInterruptException;
import com.openthinks.webscheduler.task.TaskRefDefinitionDescriber;
import com.openthinks.webscheduler.task.TaskRefProtected;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class SafaribooksonlineGetterGroupTask implements SupportTaskDefinition {

	@Override
	public void execute(TaskContext context) {
		ProcessLogger.debug(getClass() + ":Job start...");
		SafaribooksonlineGetterRef bookConfigure = new SafaribooksonlineGetterRef();
		TaskRunTimeData taskRunTimeData = getTaskRunTimeData(context).get();
		taskRunTimeData.preparedTaskRef();
		try {
			bookConfigure.readString(taskRunTimeData.getTaskRefContent());
			TaskRefProtected.valueOf(getClass()).protect(bookConfigure);
			ProcessLogger.debug(bookConfigure.toString());
			Optional<String> namesOpt = bookConfigure.getProp("safaribook-names");
			Optional<String> urlsOpt = bookConfigure.getProp("pages-catalog-urls");
			Optional<String> pagelinksOpt = bookConfigure.getProp("catalog-pagelinks-selectors");
			if (!namesOpt.isPresent() || !urlsOpt.isPresent()) {
				raise("Not valid configure item:safaribook-names/pages-catalog-urls");
			}
			String[] nameArray = namesOpt.get().trim().split(ATTR_VALUE_SPLIT_TOKEN);
			String[] urlArray = urlsOpt.get().trim().split(ATTR_VALUE_SPLIT_TOKEN);
			Checker.require(nameArray).sameLengthWith(urlArray);
			if (!pagelinksOpt.isPresent()) {
				raise("Not valid configure item:catalog-pagelinks-selectors");
			}
			String[] linkArray = pagelinksOpt.get().trim().split(ATTR_VALUE_SPLIT_TOKEN);

			for (int i = 0; i < nameArray.length; i++) {
				SafaribooksonlineGetterRef config = (SafaribooksonlineGetterRef) bookConfigure.clone();
				config.setBookName(nameArray[i]);
				config.setCatalogPageUrl(urlArray[i]);
				if (i < linkArray.length) {
					config.setPageLinkOfCatalogSelector(linkArray[i]);
				} else {
					config.setPageLinkOfCatalogSelector(linkArray[0]);
				}
				try {
					SafariBookLaunch bookLaunch = new SafariBookLaunch(config);
					bookLaunch.start();
					taskRunTimeData.getLastTaskResult()
							.track(StaticUtils.formatNow() + ": Success to download book[" + nameArray[i] + "].");
				} catch (Exception e) {
					ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e);
					taskRunTimeData.getLastTaskResult().track(StaticUtils.formatNow() + ": Failed to download book["
							+ nameArray[i] + "]. Error message: " + e.getMessage());
				}
				context.syncTaskRuntimeData();
			}

		} catch (Exception e) {
			ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod(), e);
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
		describer.push("#[required]download books folder name, split by semicolon(;)");
		describer.push("safaribook-names=book_folder_name1;book_folder_name2");
		describer.push("#[required] download books urls, split by semicolon(;)");
		describer.push(
				"#The catalog page url,just like: http://techbus.safaribooksonline.com/book/databases/oracle-pl-sql/9780133798548");
		describer.push("pages-catalog-urls=url1;url2");
		describer.push(
				"#The css selector for each download page anchor on catalog page, just like: div.catalog_container a[href*='9781784397203'], split by semicolon(;) ");
		describer.push(
				"catalog-pagelinks-selectors=div.catalog_container a[href*='book number1'];div.catalog_container a[href*='book number2'];");
		describer.push("#-------------------------------------------------");
		describer.push("#[option]Browser client: FF45; FF52; IE; EDGE; CHROME");
		describer.push("browser-version=FF52");
		describer.push("#[option]proxy host if present");
		describer.push("proxy-host=http://example.proxy.com");
		describer.push("#[option]proxy host port");
		describer.push("proxy-port=80");
		describer.push("#[option]proxy auth user name");
		describer.push("proxy-auth-user=your proxy auth name");
		describer.push("#[option]proxy auth pass");
		describer.push("proxy-auth-pass=your proxy auth pass");
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
		describer.push("#show message in CMD, the log level include: DEBUG,INFO,WARN,ERROR,FATAL");
		describer.push("logger-level=INFO");

	}

	@Override
	public TaskDefinitionDescriber getTaskDescriber() {
		return TaskDefinitionDescriber.build(getClass()).push("Safari book group download task.");
	}
}
