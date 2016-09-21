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
<title>Setting - Role</title>
<%@ include file="../../template/head.style.jsp"%>
<link rel="stylesheet" href="${ew:pathS('/static/bootstrap-select/css/bootstrap-select.min.css')}">
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
					Setting <small>Roles</small>
				</h1>
				<form action="${ew:path('/setting/role/edit')}" method="post"
					class="form-horizontal">
					<div class="form-group">
						<label for="rolename" class="col-sm-2 control-label">Role
							name</label>
						<div class="col-sm-10">
							<input type="hidden" class="form-control" id="roleid"
								name="roleid" value="${role.id }">
							<input type="text" class="form-control" id="rolename"
								name="rolename" placeholder="role name" value="${role.name }" required>
						</div>
					</div>
					<div class="form-group">
						<label for="rolemaps" class="col-sm-2 control-label">Role maps</label>
						<div class="col-sm-10">
							<input type="hidden" name="rolemaps" id="rolemaps" value="${role.joinedRoleMaps }"/>
							<select name="rolemaps-sel-path" required title="Choose some of the following paths..."
								class="selectpicker show-tick " id="rolemaps-sel-path" data-width="auto"
								data-live-search="true" data-show-subtext="true" multiple data-selected-text-format="count > 3">
								<c:forEach var="webCtr" items="${webControllers }">
									<optgroup label="<code>${webCtr.relativePath }</code><small>${webCtr.name }</small>">
										<c:forEach var="webMethod" items="${webCtr.webMethods }">
											<option value="${webMethod.fullPath }" 
											data-content="<kbd>${webMethod.relativePath }</kbd><small>${webMethod.name }</small>" 
											<c:if test="${fn:contains(role.joinedRoleMaps , webMethod.fullPath)}">selected</c:if> >
											</option>
										</c:forEach>
									</optgroup>
								</c:forEach>
							</select>
							<p class="visible-xs-block visible-sm-block"></p>
							<span> <code >&nbsp;+&nbsp;</code> </span>
							<select name="rolemaps-sel-include" id="rolemaps-sel-include"  data-width="auto" class="selectpicker show-tick"
							data-show-subtext="true" multiple data-selected-text-format="count > 3"
							title="Include some of the following roles...">
								<c:forEach var="othrole" items="${roles }">
									<option value="${othrole.id }" 
									<c:if test="${fn:contains(role.joinedRoleMaps , othrole.id)}">selected</c:if> >
										${othrole.name }
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="roledesc" class="col-sm-2 control-label">Role
							description</label>
						<div class="col-sm-10">
							<textarea class="form-control" id="roledesc" name="roledesc">${role.desc }</textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10 ">
							<button type="submit" class="btn btn-primary">Apply
								Changes</button>
							<a class="btn btn-default " href="${ew:path('/setting/role')}"
								role="button">Cancel</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="../../template/body.script.jsp"%>
	<script type="text/javascript"
		src="${ew:pathS('/static/bootstrap-select/js/bootstrap-select.min.js')}"></script>
	<script type="text/javascript"
		src="${ew:pathS('/static/js/setting.role.edit.js')}"></script>
</body>
</html>