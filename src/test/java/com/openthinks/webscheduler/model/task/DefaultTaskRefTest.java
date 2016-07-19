package com.openthinks.webscheduler.model.task;

import java.io.IOException;
import java.util.Enumeration;

import org.junit.Before;
import org.junit.Test;

public class DefaultTaskRefTest {

	StringBuilder xmlContent = new StringBuilder();
	StringBuilder textContent = new StringBuilder();

	@Before
	public void setUp() {
		xmlContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlContent.append("<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">");
		xmlContent.append("<properties>");
		xmlContent.append("<entry key=\"browser-version\">FF38</entry>");
		xmlContent.append("<entry key=\"save-dir\">W:\\Book2\\Oracle_PLSQL_by_Example</entry>");
		xmlContent.append("<entry key=\"need-login\">true</entry>");
		xmlContent.append("</properties>");

		textContent.append("#browser version");
		textContent.append("\r\n");
		textContent.append("browser-version=FF38");
		textContent.append("\r\n");
		textContent.append("save-dir=W:\\Book2\\Oracle_PLSQL_by_Example");
		textContent.append("\r\n");
		textContent.append("need-login=true");
	}

	@Test
	public void testReadString() throws IOException {
		DefaultTaskRef taskRef = new DefaultTaskRef();
		taskRef.readString(xmlContent.toString());
		//		StringWriter stringWriter = new StringWriter();
		//		PrintWriter printWriter = new PrintWriter(System.out, true);
		//		taskRef.list(printWriter);
		Enumeration<?> enumeration = taskRef.propertyNames();
		while (enumeration.hasMoreElements()) {
			String propertyName = (String) enumeration.nextElement();
			System.out.println(propertyName + "=" + taskRef.getProp(propertyName));
		}
	}
}
