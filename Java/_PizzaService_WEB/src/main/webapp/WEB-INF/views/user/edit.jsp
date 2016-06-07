<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit</title>
</head>
<body>

<center>
<c:set var="pc" value="${pageContext.request.contextPath}"/>
	<form:form action="${pc}/users/${user.id}" method="POST" modelAttribute="user">
		<legend>Customer:</legend>
		<form:input type="hidden" path="id"/>
		Customer name:<form:errors path="name"/><br/>
		<form:input path="name"/><br/>
		Email address:<form:errors path="email"/><br/>
			<form:input type="text" path="email"/><br/>
<%-- 			Password:<form:errors path="password"/><br/>
			<form:input type="password" path="password"/><br/> --%>
			<input type="submit" value="Edit"/>
	</form:form>

</body>
</html>