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
<%@ include file="../../template/head.style.jsp"%>
<link rel="stylesheet" href="${ew:pathS('/static/CodeMirror/lib/codemirror.css')}">
<link rel="stylesheet" href="${ew:pathS('/static/CodeMirror/addon/display/fullscreen.css')}">
<link rel="stylesheet" href="${ew:pathS('/static/bootstrap-select/css/bootstrap-select.min.css')}">
<%-- <link rel="stylesheet" href="${ew:pathS('/static/bootstrap-switch/css/bootstrap3/bootstrap-switch.min.css')}"> --%>
<link rel="stylesheet" href="${ew:pathS('/static/css/task.css')}">
</head>
<body>
	<jsp:include page="../../template/navbar.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="../../template/sidebar.jsp">
				<jsp:param name="active" value="tasks" />
			</jsp:include>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Define Task</h1>
				<form action="${ew:path('/task/def/apply')}" method="post"
					class="form-horizontal">
					<div class="form-group">
						<!-- <label for="customtasktype" class="col-sm-4 col-md-2 col-lg-2 control-label" style="text-align:left">Custom Task Type</label> -->
						<div class="col-sm-12">
							<label for="customtasktype" class="control-label" >Custom Task Type</label>&nbsp;&nbsp;
							<select id="customtasktype" name="customtasktype" class="selectpicker show-tick " data-width="auto" data-live-search="true" data-show-subtext="true">
								<option data-content="<span class='label label-warning'>New Task Definition</span>" value=""  >New Task Definition</option>
								<option data-divider="true"></option>
								<c:forEach var="tm" items="${pm.customTasks }">
									<option value="${tm.type.name }"  <c:if test="${pm.customtasktype==tm.type.name }">selected</c:if> >${tm.type.simpleName }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<!-- <div class="form-group">
						<div class="col-sm-12">
							<label for="keepfile" class="control-label" >Keep Source File</label>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkbox" id="keepfile" name="keepfile" checked>
						</div>
					</div> -->
					
					<div class="form-group">
						<div class="col-sm-12">
							<div class="btn-toolbar" role="toolbar" id="taskdef-toolbar" aria-label="Definition Toolbar">
							  <div class="btn-group" role="group" aria-label="Definition group">
							  	<button data-role="compile" type="button" title="compile & check" class="btn btn-default"><i class="fa fa-check" aria-hidden="true"></i></button>
							  	<button data-role="example" type="button" title="show example" class="btn btn-default"><i class="fa fa-lightbulb-o" aria-hidden="true"></i></button>
							  	<button data-role="copy" type="button" title="copy to clipboard" class="btn btn-default" data-clipboard-target="#taskdef"><i class="fa fa-clipboard" aria-hidden="true"></i></button>
							  	<button data-role="clear" type="button" title="clear" class="btn btn-default"><i class="fa fa-eraser" aria-hidden="true"></i></button>
							  	<button data-role="fullscreen" type="button" title="fullscreen" class="btn btn-default"><i class="fa fa-expand" aria-hidden="true"></i></button>
							  	
							  </div>
							</div>
							<ul class="nav nav-tabs" role="tablist">
							  <li role="presentation" class="active"><a href="#taskdefcontainer" role="tab" data-toggle="tab">Source</a></li>
							  <li role="presentation"><a href="#taskdefresultcontainer" role="tab" data-toggle="tab">Result</a></li>
							</ul>
							
							<div class="tab-content">
							    <div role="tabpanel" class="tab-pane active" id="taskdefcontainer">
							    	<textarea class="form-control " id="taskdef" name="taskdef" rows="10" required></textarea>
							    </div>
							    <div role="tabpanel" class="tab-pane" id="taskdefresultcontainer">
							    	<textarea class="form-control " id="taskdefresult" name="taskdefresult" rows="10" readonly></textarea>
							    </div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12 ">
							<button type="submit" class="btn btn-primary">Apply Changes</button>
							<a class="btn btn-default " href="${ew:path('/task/index')}" role="button">Cancel</a>
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>

	<%@ include file="../../template/body.script.jsp"%>
	<script type="text/javascript" src="${ew:pathS('/static/CodeMirror/lib/codemirror.js')}"></script>
	<script type="text/javascript" src="${ew:pathS('/static/CodeMirror/mode/clike/clike.js')}"></script>
	<script type="text/javascript" src="${ew:pathS('/static/CodeMirror/addon/display/fullscreen.js')}"></script>
	<script type="text/javascript" src="${ew:pathS('/static/js/clipboard.js/clipboard.min.js')}"></script>
	<script type="text/javascript" src="${ew:pathS('/static/bootstrap-select/js/bootstrap-select.min.js')}"></script>
	<%-- <script type="text/javascript" src="${ew:pathS('/static/bootstrap-switch/js/bootstrap-switch.min.js')}"></script> --%>
	<script type="text/javascript" src="${ew:pathS('/static/js/task.definition.js')}"></script>
</body>
</html>