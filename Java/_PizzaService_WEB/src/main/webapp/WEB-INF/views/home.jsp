<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Pizza Service</title>
</head>
<body>
<h1>
	Pizza Service!  
</h1>

<div style="float: right;">
	<a href="customer?doSingUp">Sing up</a>
</div>
<a href="customer/17/edit">Edit</a>
<h2>Catalog of pizzas:</h2>

<c:forEach var="pizza" items="${listPizzas}">
	${pizza.name}, price:${pizza.price} <br/>
</c:forEach>

<%-- <P>  The time on the server is ${serverTime}. </P> --%>
</body>
</html>
