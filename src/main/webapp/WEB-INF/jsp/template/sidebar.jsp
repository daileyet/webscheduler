<div class="col-sm-3 col-md-2 sidebar">
	<ul class="nav nav-sidebar" data-active-role="${param.active}">
		<li data-role="overview"
			class="${param.active=='overview'?'active':''}"><a
			href="${pageContext.request.contextPath}/index.htm"><span
				class="glyphicon glyphicon-home" aria-hidden="true"></span> Overview</a></li>
		<li data-role="tasks" class="${param.active=='tasks'?'active':''}"><a
			href="${pageContext.request.contextPath}/task/index.htm"><span
				class="glyphicon glyphicon-tasks" aria-hidden="true"></span> Tasks</a></li>
		<li data-role="users" class="${param.active=='users'?'active':''}"><a
			href="#"><span class="glyphicon glyphicon-user"
				aria-hidden="true"></span> Users</a></li>
	</ul>
	<!-- 	<ul class="nav nav-sidebar">
		<li><a href="">Nav item</a></li>
		<li><a href="">Nav item again</a></li>
	</ul> -->
</div>

