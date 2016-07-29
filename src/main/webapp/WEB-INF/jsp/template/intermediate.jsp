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
<title>${pm.title }</title>
<%@ include file="head.style.jsp"%>
<%-- <link href="${ew:pathS('/static/css/intermediare.css')}"
	rel="stylesheet" /> --%>
	<link href="${pageContext.request.contextPath}/static/css/intermediare.css"
	rel="stylesheet" />
</head>
<body>
	<jsp:include page="navbar.jsp" />
	
	<div class="container-fluid">

		<div class="row">
			<jsp:include page="sidebar.jsp">
				<jsp:param name="active" value="${pm.activeSidebar }" />
			</jsp:include>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="bg_load"></div>
				<div class="wrapper">
					<div class="inner">
						<span>L</span> <span>o</span> <span>a</span> <span>d</span> <span>i</span>
					<span>n</span> <span>g</span>
					</div>
				</div>
				
			</div>
		</div>
	</div>

	<%@ include file="body.script.jsp"%>
	<script type="text/javascript">
		$(window).load(function() {
			setTimeout(function(){
				$(".bg_load").fadeOut("slow");
				$(".wrapper").fadeOut("slow");
				var redirectUrl = "${pm.redirectUrl}";
				if(redirectUrl && redirectUrl!=""){
					window.location = redirectUrl ;
				}
			},800);
		})
	</script>
</body>
</html>
