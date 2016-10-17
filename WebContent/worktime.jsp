<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div>This Jsp page sends requests to the Servlet "WorkTime".</div><br>
<div>It accepts two parameters and stopped instances will start and stop automaticlly.</div><br>
<form action="${pageContext.request.contextPath}/WorkTime" method="post">
	Start of workday(e.g. 8:00 AM):<input type="text" name ="start2"><br>
	End of workday(e.g. 8:30 AM):  <input type="text" name ="end2"><br>
	submit:<input type="submit" value="submit"><br>
</form>
</body>
</html>