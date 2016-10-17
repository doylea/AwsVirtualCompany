<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/my.js"></script>

<script type="text/javascript">
window.setInterval(getmessage, 2000); 
	function getmessage() {
			var xmlhttp = getXmlHttpRequest();
			xmlhttp.onreadystatechange = function() {

				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

					var msg = xmlhttp.responseText;

					document.getElementById("d").innerHTML = msg;

				}
			};

			xmlhttp.open("GET", "${pageContext.request.contextPath}/RealTimeServlet");

			xmlhttp.send(null);
			
		};
</script>
</head>
<body onload="getmessage()">
	<div id="d"></div>
</html>