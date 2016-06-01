<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit page</title>
</head>
<body>
<center>
	<form:form action="customer/${address.id}" method="post" modelAttribute="address"><%-- ${pageContext.request.contextPath}/ --%>
		<fieldset>
			<legend>Address:</legend>
			<form:input type = "hidden" path="id"/>
			Address:<form:errors path="address"/><br/>
			<form:input type="text" path="address"/><br/>
			Phone Number:<form:errors path="phoneNumber"/><br/>
			<form:input type="text" path="phoneNumber" /><br/>
		</fieldset><br/>
		<input type="submit" value="SingUp" style="height:50px; width:200px;"/>
	</form:form>
	<br/><br/><br/>
</center>


</body>
</html>