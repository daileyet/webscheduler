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
<title>Setting - Protected REFs</title>
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
				<h1 class="page-header">Setting <small>REFs</small></h1>
				<div class="row placeholders">
						<div class="col-xs-12 col-sm-12 placeholder2" >
							<a href="${ew:path('/setting/ref/sync') }" class="no-underline" >
								<img src="${ew:pathS('/static/img/savetodisk_unavaiable_512.png')}"
									  width="150" height="150" class="img-responsive"
									alt="Generic placeholder thumbnail">
								<h4>Sync</h4>
								<span class="text-muted">Sync between memory and disk</span>
							</a>
						</div>
				</div>
				<div class="row ">
					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>#</th>
									<th>Protected Task Name</th>
									<th>Protected Count</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="trp" items="${trps }" varStatus="status">
									<tr data-id="${trp.taskDefClass.name }">
										<td data-title="trpseq">${status.index+1 }</td>
										<td>${trp.taskDefClass.name}</td>
										<td>${trp.protectedCount }</td>
										<td><a href="${ew:path('/setting/ref/to/edit') }?trpid=${trp.taskDefClass.name}" >
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