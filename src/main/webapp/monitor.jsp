<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebScheduler</title>
</head>
<body>
	<%  
		String host = request.getRemoteHost();
		out.println("getRemoteHost():"+host);
		out.print("<BR>");
		try {
			InetAddress inetAddress = InetAddress.getByName(host);
			out.println("inetAddress.toString():"+inetAddress);out.print("<BR>");
			out.println("isAnyLocalAddress():"+inetAddress.isAnyLocalAddress());out.print("<BR>");
			out.println("isLoopbackAddress():"+inetAddress.isLoopbackAddress());out.print("<BR>");
			out.println("isLinkLocalAddress():"+inetAddress.isLinkLocalAddress());out.print("<BR>");
		} catch (UnknownHostException e) {
			out.println(e);
		}
		
		host = (String)request.getHeader("X-Real-IP");
		out.println("X-Real-IP:"+host);
		out.print("<BR>");
		try {
			InetAddress inetAddress = InetAddress.getByName(host);
			out.println("inetAddress.toString():"+inetAddress);out.print("<BR>");
			out.println("isAnyLocalAddress():"+inetAddress.isAnyLocalAddress());out.print("<BR>");
			out.println("isLoopbackAddress():"+inetAddress.isLoopbackAddress());out.print("<BR>");
			out.println("isLinkLocalAddress():"+inetAddress.isLinkLocalAddress());out.print("<BR>");
		} catch (UnknownHostException e) {
			out.println(e);
		}
		
		host = (String)request.getHeader("X-Forwarded-For");
		out.println("X-Forwarded-For:"+host);
		out.print("<BR>");
		try {
			InetAddress inetAddress = InetAddress.getByName(host);
			out.println("inetAddress.toString():"+inetAddress);out.print("<BR>");
			out.println("isAnyLocalAddress():"+inetAddress.isAnyLocalAddress());out.print("<BR>");
			out.println("isLoopbackAddress():"+inetAddress.isLoopbackAddress());out.print("<BR>");
			out.println("isLinkLocalAddress():"+inetAddress.isLinkLocalAddress());out.print("<BR>");
		} catch (UnknownHostException e) {
			out.println(e);
		}
	%>
</body>
</html>