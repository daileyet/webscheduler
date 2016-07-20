<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="dailey.yet@outlook.com">
<title>Error</title>
<%@ include file="head.style.jsp"%>
<link href="${ pageContext.request.contextPath}/static/css/error.css"
	rel="stylesheet">
</head>
<body>
	<jsp:include page="navbar.jsp" />

	<div class="container-fluid">

		<div class="row">
			<jsp:include page="sidebar.jsp">
				<jsp:param name="active" value="${pm.activeSidebar }" />
			</jsp:include>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<!-- Error Page Content -->
				<div class="container-fluid">
					<div class="jumbotron">
						<h1>
							<i class="fa fa-frown-o red"></i> Service Error
						</h1>
						<p class="lead">
							Sorry! Your operations was processed failed<em></em>.
						</p>
						<p>
							<a class="btn btn-default btn-lg green" href="javascript:window.top.history.back();">Try Last Page Again</a>
						</p>
					</div>
				</div>
				<div class="container-fluid ">
					<div class="body-content">
						<div class="row">
							<div class="col-md-6">
								<h2>What happened?</h2>
								<c:forEach var="entry" items="${webError}">
									<p data-key="${entry.key }">${entry.value }</p>
								</c:forEach>
							</div>
							<div class="col-md-6">
								<h2>What can I do?</h2>
								<p class="lead">If you're a site visitor</p>
								<p>If you need immediate assistance, please send us an email
									instead. We apologize for any inconvenience.</p>
								<p class="lead">If you're the site owner</p>
								<p>This error is mostly likely very brief, the best thing to
									do is to check back in a few minutes and everything will
									probably be working normal again.</p>
							</div>
						</div>
					</div>
				</div>
				<!-- End Error Page Content -->
			</div>
		</div>
	</div>

	<%@ include file="body.script.jsp"%>
</body>
</html>