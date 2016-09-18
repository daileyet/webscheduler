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
<title>Setting - Home</title>
<%@ include file="../template/head.style.jsp"%>
</head>
<body>
	<jsp:include page="../template/navbar.jsp" />
	<div class="container-fluid">
		<div class="row">
			<jsp:include page="../template/sidebar.jsp">
				<jsp:param name="active" value="settings" />
			</jsp:include>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">Setting <small>Home</small></h1>

				<div class="row placeholders">
						<div class="col-xs-6 col-sm-6 placeholder">
							<a href="${ew:path('/setting/user') }" class="no-underline">
								<img src="${ew:pathS('/static/img/user_512.png')}"
									width="200" height="200" class="img-responsive"
									alt="Generic placeholder thumbnail">
								<h4>Users</h4>
								<span class="text-muted">Manager users</span>
							</a>
						</div>
					<div class="col-xs-6 col-sm-6 placeholder">
						<a href="${ew:path('/setting/role') }" class="no-underline">
							<img src="${ew:pathS('/static/img/role_512.png')}"
								width="200" height="200" class="img-responsive"
								alt="Generic placeholder thumbnail">
							<h4>Roles</h4>
							<span class="text-muted">Manager roles</span>
						</a>
					</div>
				</div>
				<div class="row placeholders">
					<div class="col-xs-6 col-sm-6 placeholder">
							<a href="${ew:path('/setting/ref') }" class="no-underline">
								<img src="${ew:pathS('/static/img/protection_512.png')}"
									width="200" height="200" class="img-responsive"
									alt="Generic placeholder thumbnail">
								<h4>Protection</h4>
								<span class="text-muted">Protect task configuration</span>
							</a>
					</div>
					<div class="col-xs-6 col-sm-6 placeholder">
						<a href="${ew:path('/setting/misc') }" class="no-underline">
							<img src="${ew:pathS('/static/img/misc_512.png')}"
								width="200" height="200" class="img-responsive"
								alt="Generic placeholder thumbnail">
							<h4>Misc.</h4>
							<span class="text-muted">Other settings</span>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../template/body.script.jsp"%>
</body>
</html>