<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<center>
	<form action="customer?singup" method="post">
		<input type="hidden" name="action" value="singup"/>
		<fieldset>
			<legend>SingUp:</legend>
			Customer name:<br/>
			<input type="text" name="name" value="${name}"><br/>
			Address:<br/>
			<input type="text" name="address" value="${address}"><br/>
			Phone Number:<br/>
			<input type="text" name="phoneNumber" value="${phoneNumber}"><br/>
			Email address:<br/>
			<input type="text" name="email" value="${email}"/><br/>
			Password:<br/>
			<input type="password" name="password" value="${password}"><br/>
			Check Password:<br/>
			<input type="password" name="userCheckPassword" value="${userCheckPassword}"><br/>
		</fieldset><br/>
		<fmt:message key="button.singup" var="buttonSingUp"/>
		<input type="submit" value="SingUp" style="height:50px; width:200px;"/>
	</form>
	<br/><br/><br/>
</center>

</body>
</html>