<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>user profile</title>
</head>
<body>

Yours profile:<br/>
<h3>user name: ${user.name} </h3><br/>
<h3>user name: ${user.email} </h3><br/>
<a href="${pageContext.request.contextPath}/user/${user.id}/edit">Edit</a><!--  -->

</body>
</html>