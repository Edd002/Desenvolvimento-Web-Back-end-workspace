<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Entrada</title>
</head>
<body>
	<h1>Entrada</h1>
	<form  method="post" action="resultado">
		<label>Nome: </label> <input type="text" name="nome"><br>
		<label>Sobrenome: </label> <input type="text" name="sobrenome"><br>

		<input type="submit" value="Enviar">

		<br><br>

		<label>Data e hora: </label><%@ include file="WEB-INF/data.jsp" %>
	</form>
</body>
</html>