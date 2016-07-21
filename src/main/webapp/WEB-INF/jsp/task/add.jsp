<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ew" uri="http://www.openthinks.com/easyweb"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="dailey.yet@outlook.com">
<title>Task - New</title>
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
				<h1 class="page-header">New Task</h1>
				<form action="${ew:path('/task/add')}" method="post"
					class="form-horizontal">
					<div class="form-group">
						<label for="taskname" class="col-sm-2 control-label">Task
							name</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="taskname"
								name="taskname" placeholder="task name" required>
						</div>
					</div>

					<div class="form-group">
						<label for="taskname" class="col-sm-2 control-label">Task
							type</label>
						<div class="col-sm-10">
							<select id="tasktype" name="tasktype" class="form-control" required>

								<c:forEach var="typeClass" items="${types }">
									<option value="${typeClass.name }">${typeClass.simpleName }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="taskname" class="col-sm-2 control-label">Task
							Properties</label>
						<div class="col-sm-10">
							<textarea class="form-control" id="tasktref" name="taskref" rows="10"></textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10 ">
							<button type="submit" class="btn btn-primary">Create</button>
							<a class="btn btn-default " href="${ew:path('/task/index')}" role="button">Cancel</a>
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>

	<%@ include file="../template/body.script.jsp"%>

</body>
</html>