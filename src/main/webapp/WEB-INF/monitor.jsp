<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.openthinks.webscheduler.controller.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="dailey.yet@outlook.com">
<title>EasyWeb Monitor</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<div class="page-header">
			<h1>Web Container Objects</h1>
		</div>
		<div class="row " >
			<div class="container" style="text-align: right" >
				<a class="btn btn-primary " href="${pageContext.request.contextPath}/monitors/reload${requestSuffixs[0]}" role="button">Reload</a>
			</div>
		</div>
		<div class="table-responsive">

			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Controller Name</th>
						<th>Class Type</th>
						<th>Request Mapping</th>
						<th>Path</th>
						<th>Web Method</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="contr" items="${webContainer.webControllers}">
						<tr>
							<td>${contr.name}</td>
							<td>${contr.type.name}</td>
							<td>${contr.fullPath}</td>
							<td>${contr.relativePath}</td>
							<td style="padding: 0"><c:if test="${contr.size>0}">
									<div class="list-group" style="margin: 0">
										<c:forEach var="method" items="${contr.webMethods }">
											<a class="list-group-item"
												href="${method.fullPath }${requestSuffixs[0]}">${method.name }</a>
										</c:forEach>
									</div>
								</c:if> <c:if test="${contr.size==0}">
									<span>no data found</span>
								</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

</body>
</html>