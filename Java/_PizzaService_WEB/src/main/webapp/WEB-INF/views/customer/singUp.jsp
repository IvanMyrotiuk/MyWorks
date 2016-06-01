<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<center>
	<form:form action="customer?singup" method="post" modelAttribute="customer">
		<input type="hidden" name="action" value="singup"/>
		<fieldset>
			<legend>SingUp:</legend>
			Customer name:<form:errors path="name"/><br/>
			<form:input type="text" path="name"/><br/>
			Address:<br/>
			<input type="text" path="address"/><br/>
			Phone Number:<br/>
			<input type="text" path="phoneNumber" /><br/>
			Email address:<form:errors path="email"/><br/>
			<form:input type="text" path="email"/><br/>
			Password:<form:errors path="password"/><br/>
			<form:input type="password" path="password"/><br/>
			Check Password:<br/>
			<input type="password" path="userCheckPassword" /><br/>
		</fieldset><br/>
		<fmt:message key="button.singup" var="buttonSingUp"/>
		<input type="submit" value="SingUp" style="height:50px; width:200px;"/>
	</form:form>
	<br/><br/><br/>
</center>

</body>
</html>