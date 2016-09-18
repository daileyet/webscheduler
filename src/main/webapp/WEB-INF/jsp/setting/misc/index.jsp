<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ew" uri="http://www.openthinks.com/easyweb"%>
<%@ taglib prefix="wsfn"
	uri="http://www.openthinks.com/webscheduler/fns"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="dailey.yet@outlook.com">
<title>Setting - Protected REFs</title>
<%@ include file="../../template/head.style.jsp"%>
<link rel="stylesheet" href="${ew:pathS('/static/bootstrap-select/css/bootstrap-select.min.css')}">
<link rel="stylesheet" href="${ew:pathS('/static/bootstrap-switch/css/bootstrap3/bootstrap-switch.min.css')}">
</head>
<body>
	<jsp:include page="../../template/navbar.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="../../template/sidebar.jsp">
				<jsp:param name="active" value="settings" />
			</jsp:include>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">
					Setting <small>Misc</small>
				</h1>

				<div class="row ">
					<div class="col-sm-6 col-md-8">
						<div class=" ">

							<blockquote>
								<h4 id="basic">Basic</h4>
							</blockquote>
							<dl>
								<dt>App conf path</dt>
								<dd>
									<pre>${pm.basic_app_conf_path}</pre>
								</dd>
								<dt>App conf content</dt>
								<dd>
									<pre>${pm.basic_app_conf_content }</pre>
								</dd>
								<dt>Custom task source path</dt>
								<dd><pre>${pm.basic_task_source_code_path }</pre></dd>
								<dt>Custom task class path</dt>
								<dd><pre>${pm.basic_task_class_file_path }</pre></dd>
							</dl>

						</div>
						<div class=" ">
							<blockquote><h3  id="logger-monitor">Logger & Monitor</h3></blockquote>
							<dl>
								<dt>Logger level</dt> 
								<dd>
									<select id="logger_level" name="logger_level" class="selectpicker show-tick " data-width="auto" 
									data-link="${ew:path('/setting/misc/llevel/edit') }">
										<c:forEach var="level" items="${pm.logger_level_list }">
											<option value="${level }" <c:if test="${level==pm.logger_level_now }">selected</c:if>>${level }</option>
										</c:forEach>
									</select>
									<p></p>
								</dd>
								<dt>Remote monitor</dt>
								<dd>
									<input class="bootstrap-switch"  data-link="${ew:path('/setting/misc/remotable/edit') }"
									<c:if test="${pm.monitor_remotable }">checked</c:if>
									data-label-text="Access" type="checkbox" name="remote_monitor" id="remote_monitor">
								</dd>
							</dl>
						</div>
						<div class=" ">
							<blockquote><h3  id="reload">Reload</h3></blockquote>
							<p>TBD</p>
						</div>
						<div class=" ">
							<blockquote><h3  id="service">Service</h3></blockquote>
							<p>TBD</p>
						</div>
						<div class=" ">
							<blockquote><h3  id="quartz">Quartz</h3></blockquote>
							<p>TBD</p>
						</div>
					</div>
					<nav class="col-sm-3 col-md-2 bs-docs-sidebar" id="docs-nav">
						<ul
							class="nav  bs-docs-sidenav hidden-print hidden-sm hidden-xs affix"
							data-active-role="${param.active}">
							<li><a href="#basic">Basic</a></li>
							<li><a href="#logger-monitor">Logger & Monitor</a></li>
							<li><a href="#reload">Reload</a></li>
							<li><a href="#service">Service</a></li>
							<li><a href="#quartz">Quartz</a></li>
						</ul>
					</nav>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../../template/body.script.jsp"%>
	<script type="text/javascript" src="${ew:pathS('/static/bootstrap-select/js/bootstrap-select.min.js')}"></script>
	<script type="text/javascript" src="${ew:pathS('/static/bootstrap-switch/js/bootstrap-switch.min.js')}"></script>
	<script type="text/javascript" src="${ew:pathS('/static/js/setting.misc.js')}"></script>
</body>
</html>