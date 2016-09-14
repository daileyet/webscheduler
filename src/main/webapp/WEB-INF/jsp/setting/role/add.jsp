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
				<form action="${ew:path('/setting/role/add')}" method="post"
					class="form-horizontal">
					<div class="form-group">
						<label for="rolename" class="col-sm-2 control-label">Role
							name</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="rolename"
								name="rolename" placeholder="role name" required>
						</div>
					</div>
					<div class="form-group">
						<label for="roledesc" class="col-sm-2 control-label">Role
							description</label>
						<div class="col-sm-10">
							<textarea class="form-control" id="roledesc" name="roledesc"></textarea>
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
</body>
</html>