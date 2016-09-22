<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ew" uri="http://www.openthinks.com/easyweb"%>
<%@ taglib prefix="wsfn" uri="http://www.openthinks.com/webscheduler/fns"%>
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}/index.htm">WebScheduler</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${ew:path('/index')}">Dashboard</a></li>
				<c:if test="${not wsfn:isLogin(pageContext) }">
					<li><a href="#" data-toggle="modal" data-target="#login-modal">Login</a></li>
				</c:if>
				<c:if test="${wsfn:isSecurity(pageContext,'/task/index') }">
					<li><a href="${ew:path('/task/index')}" class="visible-xs-inline-block">Tasks</a></li>
				</c:if>
				<c:if test="${wsfn:isSecurity(pageContext,'/setting/index') }">
					<li><a href="${ew:path('/setting/index')}" class="visible-xs-inline-block">Settings</a></li>
				</c:if>
				<c:if test="${wsfn:isLogin(pageContext) }">
					<li><a href="#">Profile</a></li>
					<li><a href="${ew:path('/security/logout')}" class="visible-xs-inline-block">Logout</a></li>
				</c:if>
				<li><a href="${ew:path('/help')}">Help</a></li>
			</ul>
		</div>
	</div>
</nav>
<jsp:include page="./login.modal.jsp"></jsp:include>