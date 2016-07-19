<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ew" uri="http://www.openthinks.com/easyweb" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="dailey.yet@outlook.com">
<title>Task - Home</title>
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
				<h1 class="page-header">Task Home</h1>

				<div class="row placeholders">
					<a href="${ew:path('/task/to/add') }">
						<div class="col-xs-6 col-sm-6 placeholder">
							<img src="${ew:pathS('/static/img/createtask_512.png')}"
								width="200" height="200" class="img-responsive"
								alt="Generic placeholder thumbnail">
							<h4>New</h4>
							<span class="text-muted">Create a task</span>
						</div>
					</a>
					<div class="col-xs-6 col-sm-6 placeholder">
						<img src="${ew:pathS('/static/img/definetask_512.png')}"
							width="200" height="200" class="img-responsive"
							alt="Generic placeholder thumbnail">
						<h4>Define</h4>
						<span class="text-muted">Customize task definition</span>
					</div>
				</div>

				<!-- <h2 class="sub-header">Task List</h2> -->
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
							<c:forEach var="tm" items="${PAGE_TASK_LIST }">
								<tr data-id="${tm.taskId }">
								<td>${tm.taskSeq }</td>
								<td>${tm.taskName }</td>
								<td><span title="${tm.taskType}">${tm.taskTypeShort }</span></td>
								<td>${tm.taskState }</td>
								<td><a href="${ew:path('/task/schedule') }?taskid=${tm.taskId }"><span title="Schedule"
										class="glyphicon glyphicon-play" aria-hidden="true"></span></a> <a
									href="${ew:path('/task/stop') }?taskid=${tm.taskId }"><span title="Stop"
										class="glyphicon glyphicon-pause" aria-hidden="true"></span></a> <a
									href="${ew:path('/task/to/edit') }?taskid=${tm.taskId }"><span title="Edit"
										class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
									<a href="${ew:path('/task/remove') }?taskid=${tm.taskId }"><span title="Remove"
										class="glyphicon glyphicon-trash" aria-hidden="true"></span></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../template/body.script.jsp"%>

</body>
</html>