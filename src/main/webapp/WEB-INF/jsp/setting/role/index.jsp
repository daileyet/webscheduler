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
				<h1 class="page-header">Setting <small>Roles</small></h1>
				<div class="row placeholders">
						<div class="col-xs-12 col-sm-12 placeholder2">
							<a href="${ew:path('/setting/role/to/add') }" class="no-underline">
								<img src="${ew:pathS('/static/img/groupadd_512.png')}"
									  width="150" height="150" class="img-responsive"
									alt="Generic placeholder thumbnail">
								<h4>New</h4>
								<span class="text-muted">Create a role</span>
							</a>
						</div>
				</div>
				<div class="row ">
					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>#</th>
									<th>Name</th>
									<th>Desc</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="role" items="${roles }" varStatus="status">
									<tr data-id="${role.id }">
										<td data-title="roleseq">${status.index+1 }</td>
										<td>${role.name}</td>
										<td>${role.desc}</td>
										<td><a href="${ew:path('/setting/role/to/edit') }?roleid=${role.id }" >
													<span title="Edit" class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a> </td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div> 				
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../../template/body.script.jsp"%>

</body>
</html>