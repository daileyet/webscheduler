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
<link rel="stylesheet" href="${ew:pathS('/static/CodeMirror/lib/codemirror.css')}">
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
					Setting <small>REFs</small>
				</h1>
				<form action="${ew:path('/setting/ref/edit')}" method="post"
					class="form-horizontal">
					<div class="form-group">
						<label for="protectedname" class="col-sm-2 control-label">Protected task name</label>
						<div class="col-sm-10">
							<input type="hidden" class="form-control" id="trpid"
								name="trpid"  value="${trp.taskDefClass.name }">
							<p class="form-control-static">${trp.taskDefClass.name}
								<a href="#" title="show all REFs" data-toggle="modal" data-target="#allref-info">
									<i class="fa fa-lightbulb-o" aria-hidden="true" ></i>
								</a>
							</p>
						</div>
					</div>
					<div class="form-group">
						<label for="protectedref" class="col-sm-2 control-label">Protected task
							properties</label>
						<div class="col-sm-10">
							<textarea class="form-control ref-editor" id="protectedref" name="protectedref" rows="10">${trp.taskRefFormat }</textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10 ">
							<button type="submit" class="btn btn-primary">Apply Changes</button>
							<a class="btn btn-default " href="${ew:path('/setting/ref')}" role="button">Cancel</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="modal fade" id="allref-info" tabindex="-1" role="dialog" aria-labelledby="info" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header" style="padding:10px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">All REFs</h4>
                </div>
                <div class="modal-body" style="padding:5px;">
                    <textarea id="allref">${trp.taskRefDescriber.description }</textarea>
               </div>
            </div>
        </div>
    </div>
	<%@ include file="../../template/body.script.jsp"%>
	<script type="text/javascript" src="${ew:pathS('/static/CodeMirror/lib/codemirror.js')}"></script>
	<script type="text/javascript" src="${ew:pathS('/static/CodeMirror/mode/properties/properties.js')}"></script>
	<script type="text/javascript" src="${ew:pathS('/static/CodeMirror/mode/xml/xml.js')}"></script>
	<script type="text/javascript" src="${ew:pathS('/static/js/setting.ref.edit.js')}"></script>
</body>
</html>