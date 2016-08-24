<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="wsfn" uri="http://www.openthinks.com/webscheduler/fns"%>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script
	src="${pageContext.request.contextPath}/static/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script
	src="${pageContext.request.contextPath}/static/bootstrap/3.3.7/js/bootstrap.min.js"
	crossorigin="anonymous"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script
	src="${pageContext.request.contextPath}/static/js/ie10-viewport-bug-workaround.js"></script>
	
<c:if test="${not wsfn:isLogin(pageContext) }">	
	<script
		src="${pageContext.request.contextPath}/static/js/login.modal.js"></script>
</c:if>
