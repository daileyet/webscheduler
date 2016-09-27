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
<link rel="icon" href="${pageContext.request.contextPath}/static/favicon.ico">
<title>Profile</title>
<%@ include file="./template/head.style.jsp" %>
</head>
<body>
	<jsp:include page="./template/navbar.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="./template/sidebar.jsp" >
				<jsp:param name="active" value="overview" />
			</jsp:include>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Profile</h1>

				<div class="row ">
					<blockquote><p>Base info</p></blockquote>
					<form class="form-horizontal" method="post" action="${ew:path('/profile/base/edit') }">
						<div class="form-group">
							<label for="userid" class="col-sm-2 control-label">User ID</label>
							<div class="col-sm-10">
								<p class="form-control-static">${user.id}</p>
							</div>
						</div>
						<div class="form-group">
							<label for="username" class="col-sm-2 control-label">User name</label>
							<div class="col-sm-10">
								<p class="form-control-static">${user.name}</p>
							</div>
						</div>
						<div class="form-group">
							<label for="useremail" class="col-sm-2 control-label">User email</label>
							<div class="col-sm-10">
								<p class="form-control-static">
									<span id="useremail-static">${user.email}</span> 
									<a href="#" id="useremail-edit-link"><span title="Edit" class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
								</p>
								<input type="email" name="useremail" id="useremail" value="${user.email}" class="form-control hidden" required>
							</div>
						</div>
						<div class="form-group">
							<label for="userroles" class="col-sm-2 control-label">User
								roles</label>
							<div class="col-sm-10">
								<p class="form-control-static">
									<c:forEach var="role" items="${user.roles }">
										<kbd>${role.name}</kbd> 
									</c:forEach>
								</p>
							</div>
						</div>
						<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10 ">
							<button type="submit" id="btn-apply1" class="btn btn-primary hidden">Apply Changes</button>
							<a class="btn btn-default" id="btn-cancel1" href="#" role="button">Cancel</a>
						</div>
					</div>
					</form>
				</div>
				
				<div class="row">
					<blockquote><p>Change password</p></blockquote>
					<form action="${ew:path('/profile/pwd/edit') }" class="form-horizontal" method="post">
						<div class="form-group">
							<label for="userpass" class="col-sm-2 control-label">Old password</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" name="userpass1" id="userpass1" required="required">
							</div>
						</div>
						<div class="form-group">
							<label for="userpass" class="col-sm-2 control-label">New password</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" name="userpass" id="userpass" required="required">
							</div>
						</div>
						<div class="form-group">
							<label for="userpass" class="col-sm-2 control-label">Type again</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" name="userpass2" id="userpass2" required="required">
							</div>
						</div>
						<div class="col-sm-offset-2 col-sm-10 ">
							<button type="submit" id="btn-apply2" class="btn btn-primary ">Apply Changes</button>
							<button type="reset" id="btn-cancel2" class="btn btn-default">Cancel</button>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>

	<%@ include file="./template/body.script.jsp" %>
	<script src="${ew:pathS('/static/js/profile.js') }"></script>
</body>
</html>