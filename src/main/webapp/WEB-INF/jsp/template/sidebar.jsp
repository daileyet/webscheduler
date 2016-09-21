<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ew" uri="http://www.openthinks.com/easyweb"%>
<%@ taglib prefix="wsfn" uri="http://www.openthinks.com/webscheduler/fns"%>
<div class="col-sm-3 col-md-2 sidebar">
	<ul class="nav nav-sidebar" data-active-role="${param.active}">
		<li data-role="overview"
			class="${param.active=='overview'?'active':''}"><a
			href="${ew:path('/index')}"><span
				class="glyphicon glyphicon-home" aria-hidden="true"></span> Overview</a></li>
		<c:if test="${wsfn:isSecurity(pageContext,'/task/index') }">
			<li data-role="tasks" class="${param.active=='tasks'?'active':''}"><a
				href="${ew:path('/task/index')}"><span
					class="glyphicon glyphicon-tasks" aria-hidden="true"></span> Tasks</a></li>
		</c:if>
		<c:if test="${wsfn:isSecurity(pageContext,'/setting/index') }">
			<li data-role="settings" class="${param.active=='settings'?'active':''}"><a
				href="${ew:path('/setting/index')}"><span class="glyphicon glyphicon-cog"
					aria-hidden="true"></span> Settings</a></li>
		</c:if>
		<c:if test="${wsfn:isLogin(pageContext) }">
			<li data-role="exit" class="${param.active=='exit'?'active':''}"><a
				href="${ew:path('/security/logout')}"><span class="glyphicon glyphicon-off"
					aria-hidden="true"></span> Logout</a></li>
		</c:if>
	</ul>
	
</div>

