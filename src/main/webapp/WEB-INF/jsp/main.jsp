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
<link rel="icon"
	href="${pageContext.request.contextPath}/static/favicon.ico">
<title>Dashboard</title>
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
				<h1 class="page-header">Dashboard</h1>

				<div class="row placeholders">
					<div class="col-xs-12 col-sm-6  placeholder">
						<div style="max-width:400px;">
							<canvas id="chart1" class="chart" width="200" height="200"  class="img-responsive" data-link="${ew:path('/chart1') }"></canvas>
							<h4>Task Types</h4>
							<span class="text-muted">Something else</span>
						</div>
					</div>
					<div class="col-xs-12 col-sm-6 placeholder">
						<div style="max-width:400px;">
							<canvas  id="chart2" class="chart" width="200" height="200"  class="img-responsive" data-link="${ew:path('/chart2') }"></canvas>
							<h4>Task States</h4>
							<span class="text-muted">Something else</span>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<%@ include file="./template/body.script.jsp" %>
	<script src="${ew:pathS('/static/Chart.js/Chart.bundle.min.js') }"></script>
	<script src="${ew:pathS('/static/js/dashboard.js') }"></script>
</body>
</html>