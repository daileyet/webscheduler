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
<title>Setting - User</title>
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
				<h1 class="page-header">Setting <small>Users</small></h1>
				<div class="row placeholders">
						<div class="col-xs-6 col-sm-6 placeholder2">
							<a href="${ew:path('/setting/user/to/add') }" class="no-underline">
								<img src="${ew:pathS('/static/img/useradd_512.png')}"
									  width="150" height="150" class="img-responsive"
									alt="Generic placeholder thumbnail">
								<h4>New</h4>
								<span class="text-muted">Create a user</span>
							</a>
						</div>
						<div class="col-xs-6 col-sm-6 placeholder" >
							<a href="${ew:path('/setting/user/sync') }" class="no-underline" >
								<c:if test="${isInSync }">
									<img src="${ew:pathS('/static/img/savetodisk_512.png')}" title="In sync"
										  width="150" height="150" class="img-responsive"
										alt="Generic placeholder thumbnail">
								</c:if>
								<c:if test="${not isInSync }">
									<img src="${ew:pathS('/static/img/savetodisk_unavaiable_512.png')}" title="Out of sync"
										  width="150" height="150" class="img-responsive"
										alt="Generic placeholder thumbnail">
								</c:if>
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
									<th>Name</th>
									<th>Roles</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="user" items="${users }" varStatus="status">
									<tr data-id="${user.id }">
										<td data-title="userseq">${status.index+1 }</td>
										<td data-title='username'>${user.name}</td>
										<td>
											<c:forEach var="role" items="${user.roles }">
												<kbd>${role.name}</kbd> 
											</c:forEach>
										</td>
										<td>
											<a href="${ew:path('/setting/user/to/edit') }?uid=${user.id }" >
													<span title="Edit" class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
											<a href="#" data-href="${ew:path('/setting/user/remove') }?uid=${user.id }"  data-confirm="true"  data-toggle="modal" data-target="#confirm-delete">
													<span title="Remove" class="glyphicon glyphicon-trash text-danger" aria-hidden="true"></span>
												</a> 
										</td>
											
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div> 				
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="confirm" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Confirm Remove</h4>
                </div>
            
                <div class="modal-body">
                    <p>You are going to remove this user <span class="label label-default" data-title="username"></span>, this procedure is irreversible.</p>
                    <p>Do you want to proceed?</p>
                </div>
                
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-danger btn-ok">Delete</a>
                </div>
            </div>
        </div>
    </div>

	<%@ include file="../../template/body.script.jsp"%>
	<script type="text/javascript"
		src="${ew:pathS('/static/js/setting.user.index.js')}"></script>
</body>
</html>