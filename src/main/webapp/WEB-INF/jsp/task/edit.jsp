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
<title>Task - Edit</title>
<%@ include file="../template/head.style.jsp"%>
<link rel="stylesheet" href="${ew:pathS('/static/CodeMirror/lib/codemirror.css')}">
<link rel="stylesheet" href="${ew:pathS('/static/css/task.css')}">
</head>
<body>
	<jsp:include page="../template/navbar.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="../template/sidebar.jsp">
				<jsp:param name="active" value="tasks" />
			</jsp:include>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Edit Task</h1>
				<form action="${ew:path('/task/edit')}" method="post"
					class="form-horizontal">
					<div class="form-group">
						<label for="taskid" class="col-sm-2 control-label">Task
							ID</label>
						<div class="col-sm-10">
							<input type="hidden" class="form-control" id="taskid"
								name="taskid"  value="${pm.tm.taskId }">
							<p class="form-control-static">${pm.tm.taskId }</p>
						</div>
					</div>
					
					<div class="form-group">
						<label for="taskname" class="col-sm-2 control-label">Task
							name</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="taskname"
								name="taskname" placeholder="task name" 
								value="${pm.tm.taskName }"
								required>
						</div>
					</div>

					<div class="form-group">
						<label for="taskname" class="col-sm-2 control-label">Task
							type</label>
						<div class="col-sm-10">
							<select id="tasktype" name="tasktype" class="form-control" required>

								<c:forEach var="task" items="${pm.supportTasks }" varStatus="status">
									<option <c:if test="${pm.tm.taskType==task.type.name }">selected</c:if> 
									data-required="${task.refDescriber.required }" data-ref="st_${status.index }" value="${task.type.name }" title="${task.describer.description }">${task.type.simpleName }</option>
								</c:forEach>
								<option value="" disabled="disabled">--------------------------</option>
								<c:forEach var="task" items="${pm.customTasks }" varStatus="status">
									<option <c:if test="${pm.tm.taskType==task.type.name }">selected</c:if>  
									data-required="${task.refDescriber.required }" data-ref="ct_${status.index }" value="${task.type.name }" title="${task.describer.description }">${task.type.simpleName }</option>
								</c:forEach>
							</select>
							<div class="hidden" id="tasktype-ref">
								<c:forEach var="task" items="${pm.supportTasks }" varStatus="status">
									<code id="st_${status.index }" >${task.refDescriber.description }</code>
								</c:forEach>
								<c:forEach var="task" items="${pm.customTasks }" varStatus="status">
									<code id="ct_${status.index }">${task.refDescriber.description }</code>
								</c:forEach>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="taskname" class="col-sm-2 control-label">Task
							Properties</label>
							
						<div class="col-sm-10">
							<div class="btn-toolbar" role="toolbar" id="taskref-toolbar" aria-label="Ref Toolbar">
							  <div class="btn-group" role="group" aria-label="">
							  	<button data-role="example" type="button" title="show example" class="btn btn-default"><i class="fa fa-lightbulb-o" aria-hidden="true"></i></button>
							  	<button data-role="copy" type="button" title="copy to clipboard" class="btn btn-default" data-clipboard-target="#taskref"><i class="fa fa-clipboard" aria-hidden="true"></i></button>
							  	<button data-role="clear" type="button" title="clear" class="btn btn-default"><i class="fa fa-eraser" aria-hidden="true"></i></button>
							  </div>
							</div>
							<textarea class="form-control"  id="taskref" name="taskref" rows="10">${pm.tm.taskRefContent}</textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10 ">
							<button type="submit" class="btn btn-primary">Apply Changes</button>
							<a class="btn btn-default " href="${ew:path('/task/index')}" role="button">Cancel</a>
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>

	<%@ include file="../template/body.script.jsp"%>
	<script type="text/javascript" src="${ew:pathS('/static/CodeMirror/lib/codemirror.js')}"></script>
	<script type="text/javascript" src="${ew:pathS('/static/CodeMirror/mode/properties/properties.js')}"></script>
	<script type="text/javascript" src="${ew:pathS('/static/CodeMirror/mode/xml/xml.js')}"></script>
	<script type="text/javascript" src="${ew:pathS('/static/js/clipboard.js/clipboard.min.js')}"></script>
	<script type="text/javascript" src="${ew:pathS('/static/js/task.edit.js')}"></script>
</body>
</html>