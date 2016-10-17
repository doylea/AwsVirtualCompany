<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Assignment_1</title>
</head>
<body>
<div>This Jsp page sends requests to the Servlet "AwsServlet".</div><br>
<div>It will start the instances after submitted and turns to Jsp page status.jsp, which shows the No.running instances,instance ID and state, bucket names and secret text.</div><br>
<div>If number of employees is not between 1-3 or it is not work time, it will returns to the hw1.jsp.</div><br>
<div>It also creates a bucket with the bucket name typed in. </div>
<form action="${pageContext.request.contextPath}/AwsServlet" method="post">
	${msg}<br>
	number of employees(max 3): <input type="text" name="number"><br>
	Start of workday(e.g. 8:00 AM):<input type="text" name ="start"><br>
	End of workday(e.g. 8:30 AM):  <input type="text" name ="end"><br>
	bucket name(only lower case): <input type="text" name = "bucket"><br>
	Secret text: <input type="text" name = "secret"><br>
	submit:<input type="submit" value="submit"><br>
</form>
</body>
</html>