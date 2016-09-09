<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ew" uri="http://www.openthinks.com/easyweb"%>
<%@ taglib prefix="wsfn" uri="http://www.openthinks.com/webscheduler/fns"%>
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
				<h1 class="page-header">Task <small>Home</small></h1>

				<div class="row placeholders">
						<div class="col-xs-6 col-sm-6 placeholder">
							<a href="${ew:path('/task/to/add') }" class="no-underline">
								<img src="${ew:pathS('/static/img/createtask_512.png')}"
									width="200" height="200" class="img-responsive"
									alt="Generic placeholder thumbnail">
								<h4>New</h4>
								<span class="text-muted">Create a task</span>
							</a>
						</div>
					<div class="col-xs-6 col-sm-6 placeholder">
						<a href="${ew:path('/task/def/index') }" class="no-underline">
							<img src="${ew:pathS('/static/img/definetask_512.png')}"
								width="200" height="200" class="img-responsive"
								alt="Generic placeholder thumbnail">
							<h4>Define</h4>
							<span class="text-muted">Customize task definition</span>
						</a>
					</div>
				</div>

				<!-- <h2 class="sub-header">Task List</h2> -->
				<div
					class="table-responsive <c:if test="${fn:length(tms)==0 }">hidden</c:if>">
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
							<c:forEach var="tm" items="${tms }" varStatus="status">
								<tr data-id="${tm.taskId }">
									<td data-title="taskseq">${status.index+1 }</td>
									<td data-title="taskname"><a href="${ew:path('/task/to/view') }?taskid=${tm.taskId }">${tm.taskName }</a></td>
									<td data-title="tasktype"><span title="${tm.taskType}">${tm.taskTypeShort }</span></td>
									<td data-title="taskstate">${tm.taskState.display }
										<c:if
											test="${wsfn:isCompleteWith(tm,true) }">
											<i class="fa fa-check-circle text-success" aria-hidden="true"></i>
										</c:if> <c:if test="${wsfn:isCompleteWith(tm,false) }">
											<i class="fa fa-times-circle-o text-danger"
												aria-hidden="true"></i>
										</c:if>
									</td>
									<td data-title="taskaction">
										<!-- <div class="btn-group btn-group-xs" role="group"> -->
											<c:if test="${wsfn:isAvaiableWith(tm,'Schedule') }">
												<a href="${ew:path('/task/schedule') }?taskid=${tm.taskId }" >
												<span title="Schedule" class="glyphicon glyphicon-log-in" aria-hidden="true"></span></a> 
											</c:if>
											<c:if test="${wsfn:isAvaiableWith(tm,'UnSchedule') }">
												<a href="${ew:path('/task/unschedule') }?taskid=${tm.taskId }" >
												<span title="UnSchedule" class="glyphicon glyphicon-log-out text-danger" aria-hidden="true"></span></a> 
											</c:if>
											<c:if test="${wsfn:isAvaiableWith(tm,'Stop') }">
												<a href="${ew:path('/task/stop') }?taskid=${tm.taskId }" >
													<span title="Stop" class="glyphicon glyphicon-pause" aria-hidden="true"></span></a> 
											</c:if>
											<c:if test="${wsfn:isAvaiableWith(tm,'Edit') }">
												<a href="${ew:path('/task/to/edit') }?taskid=${tm.taskId }" >
													<span title="Edit" class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a> 
											</c:if>
											<c:if test="${wsfn:isAvaiableWith(tm,'Remove') }">
												<a href="${ew:path('/task/remove') }?taskid=${tm.taskId }"  data-confirm="true"  data-toggle="modal" data-target="#confirm-delete">
													<span title="Remove" class="glyphicon glyphicon-trash text-danger" aria-hidden="true"></span>
												</a>
											</c:if>
										<!-- </div> -->
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	 <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="confirm" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Confirm Remove</h4>
                </div>
            
                <div class="modal-body">
                    <p>You are going to remove this task <span class="label label-default" data-title="taskname"></span>, this procedure is irreversible.</p>
                    <p>Do you want to proceed?</p>
                </div>
                
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-danger btn-ok">Delete</a>
                </div>
            </div>
        </div>
    </div>
	<%@ include file="../template/body.script.jsp"%>
	<script type="text/javascript"
		src="${ew:pathS('/static/js/task.index.js')}"></script>
</body>
</html>