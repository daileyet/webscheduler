package com.openthinks.webscheduler.help.source;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.JavaFileObject.Kind;

import com.openthinks.webscheduler.help.StaticDict;

/**
	 * @author dailey.yet@outlook.com
	 *
	 */
public class TaskDefSourceCodeInfo implements SourceCodeInfo {
	private String sourceCode;
	private static Pattern validateRegex = Pattern
			.compile("(?:\\s?)public(?:\\s+)(?:class)(?:\\s+)(\\w+)(?:.+)(?:(?=CustomTaskDefinition\\b))(?:.+)");

	private transient Matcher matcher;
	private boolean validate;
	private String className;

	public TaskDefSourceCodeInfo(String defSourceCode) {
		this.sourceCode = defSourceCode;
		this.compile();
	}

	protected void compile() {
		matcher = validateRegex.matcher(this.sourceCode);
		validate = matcher.find();
		try {
			className = matcher.group(1);
		} catch (Exception e) {
		}
	}

	@Override
	public boolean validate() {
		return validate;
	}

	@Override
	public String getFileName() {
		return className + Kind.SOURCE.extension;
	}

	@Override
	public String getClassFullName() {
		return StaticDict.CUSTOM_TASK_DEF_PACKAGE + "." + className;
	}

}