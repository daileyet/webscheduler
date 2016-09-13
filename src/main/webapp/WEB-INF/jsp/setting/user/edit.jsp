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
<title>Setting - User</title>
<%@ include file="../../template/head.style.jsp"%>
<link rel="stylesheet"
	href="${ew:pathS('/static/bootstrap-select/css/bootstrap-select.min.css')}">
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
					Setting <small>Users</small>
				</h1>
				<form action="${ew:path('/setting/user/edit')}" method="post"
					class="form-horizontal">
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">User
							name</label>
						<div class="col-sm-10">
							<input type=hidden class="form-control" id="uid"
								name="uid" value="${user.id }">
							<p class="form-control-static">${user.name  }</p>
						</div>
					</div>
					<div class="form-group">
						<label for="useremail" class="col-sm-2 control-label">User
							email</label>
						<div class="col-sm-10">
							<input type="email" class="form-control" id="useremail"
								name="useremail" placeholder="user email" value="${user.email }" required>
						</div>
					</div>
					<div class="form-group">
						<label for="userpass" class="col-sm-2 control-label">User
							password</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" id="userpass"
								name="userpass" placeholder="user password" value="${user.pass }" required>
						</div>
					</div>
					<div class="form-group">
						<label for="userroles" class="col-sm-2 control-label">User
							roles</label>
						<div class="col-sm-10">
							<input type="hidden" name="userroles" id="userroles" value=${user.joinedKeys } />
							<select name="userroles-sel" required
								class="selectpicker show-tick " id="userroles-sel" data-width="auto"
								data-live-search="true" data-show-subtext="true" multiple data-selected-text-format="count > 3">
								<c:forEach var="role" items="${roles }">
									<option value="${role.id}"  <c:if test="${fn:contains(user.joinedKeys , role.id)}">selected</c:if> >${role.name }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10 ">
							<button type="submit" class="btn btn-primary">Apply
								Changes</button>
							<a class="btn btn-default " href="${ew:path('/setting/user')}"
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
		src="${ew:pathS('/static/js/setting.user.edit.js')}"></script>
</body>
</html>