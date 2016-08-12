<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="dailey.yet@outlook.com">
<link rel="icon"
	href="${pageContext.request.contextPath}/static/favicon.ico">
<title>Help</title>
<%@ include file="./template/head.style.jsp"%>
</head>
<body data-spy="scroll" data-target="#docs-nav" style="position:relative">
	<jsp:include page="./template/navbar.jsp" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-9  col-md-10 main">
				<div class=" ">
					<h1 class="page-header" id="task">Task</h1>
					<p>TBD</p>
				</div>
				<div class=" ">
					<h1 class="page-header" id="trigger">Trigger</h1>
					<h2 id="trigger-cron">Cron trigger</h2>
					<p>With CronTrigger, you can specify firing-schedules such as
						“every Friday at noon”, or “every weekday and 9:30 am”, or even
						“every 5 minutes between 9:00 am and 10:00 am on every Monday,
						Wednesday and Friday during January”.</p>
					<blockquote>
						<p><code>* * * * * ?</code> At every minute. </p>
						<p><code>0 * * * * ?</code> At every hour, on the hour.</p>
						<p><code>0 */2 * * * ?</code> At every 0th minute past the 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20 and 22nd hour.</p>
						
					</blockquote>
					<p>See more details to <a href="http://www.quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/tutorial-lesson-06.html">Quartz CronTrigger</a></p>
					<p>See <a href="http://www.cronmaker.com/">CronMaker</a>. 
					CronMaker is a utility which helps you to build cron expressions. CronMaker uses Quartz open source scheduler. Generated expressions are based on Quartz cron format</p>
				</div>
				<div class=" ">
					<h1 class="page-header" id="security">Security</h1>
					<p>TBD</p>
				</div>
			</div>
			<nav class="col-sm-3 col-md-2 bs-docs-sidebar" id="docs-nav">
				<ul class="nav  bs-docs-sidenav hidden-print hidden-sm hidden-xs affix" data-active-role="${param.active}">
					<li><a href="#task">Task</a></li>
					<li><a href="#trigger">Trigger</a>
						<ul class="nav">
							<li><a href="#trigger-cron">Cron trigger</a></li>
						</ul></li>
					<li><a href="#security">Security</a></li>
				</ul>
			</nav>
		</div>
	</div>

	<%@ include file="./template/body.script.jsp"%>
</body>
</html>