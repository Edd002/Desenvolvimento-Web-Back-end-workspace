<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>C�digo Identificador</title>
</head>
<body>
	<h1>Informe um C�digo Identificador</h1>
	<form method="post" action="classifica">

		<label>C�digo verificador: </label>
		<input type="text" name="codigo">
		<br>
		<input type="submit" value="Enviar">

	</form>

	<br><br>

	<%= request.getAttribute("mensagemCodigo") %>
</body>
</html>