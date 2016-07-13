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
<title>Task</title>
<%@ include file="../template/head.style.jsp"%>
</head>
<body>
	<jsp:include page="../template/navbar.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="../template/sidebar.jsp">
				<jsp:param name="active" value="tasks" />
			</jsp:include>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Task</h1>

				<div class="row placeholders">
					<div class="col-xs-6 col-sm-6 placeholder">
						<img
							src="${pageContext.request.contextPath}/static/img/createtask_512.png"
							width="200" height="200" class="img-responsive"
							alt="Generic placeholder thumbnail">
						<h4>New</h4>
						<span class="text-muted">Create a task</span>
					</div>
					<div class="col-xs-6 col-sm-6 placeholder">
						<img
							src="${pageContext.request.contextPath}/static/img/definetask_512.png"
							width="200" height="200" class="img-responsive"
							alt="Generic placeholder thumbnail">
						<h4>Define</h4>
						<span class="text-muted">Customize task definition</span>
					</div>
				</div>

				<h2 class="sub-header">Task List</h2>
				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>#</th>
								<th>Name</th>
								<th>Type</th>
								<th>State</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1,001</td>
								<td>Lorem</td>
								<td>ipsum</td>
								<td>dolor</td>
								<td>
									<a href="#"><span title="Schedule" class="glyphicon glyphicon-play" aria-hidden="true"></span></a>
									<a href="#"><span title="Stop" class="glyphicon glyphicon-pause" aria-hidden="true"></span></a>
									<a href="#"><span title="Edit" class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
									<a href="#"><span title="Remove" class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
								</td>
							</tr>
							<tr>
								<td>1,002</td>
								<td>amet</td>
								<td>consectetur</td>
								<td>adipiscing</td>
								<td>elit</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../template/body.script.jsp"%>

</body>
</html>