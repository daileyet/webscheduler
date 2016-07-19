package com.openthinks.webscheduler.controller;

import com.openthinks.easyweb.annotation.Controller;
import com.openthinks.easyweb.annotation.Mapping;
import com.openthinks.easyweb.context.handler.WebAttributers;

@Controller
public class IndexController {

	@Mapping("/index")
	public String index(WebAttributers was) {
		return "WEB-INF/jsp/main.jsp";
	}

	@Mapping("/test")
	public String test(WebAttributers was) {
		return "WEB-INF/jsp/template/intermediate.jsp";
	}
}
