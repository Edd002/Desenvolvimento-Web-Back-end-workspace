<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Código Identificador</title>
</head>
<body>
	<h1>Informe um Código Identificador</h1>
	<form method="post" action="classifica">

		<label>Código verificador: </label>
		<input type="text" name="codigo">
		<br>
		<input type="submit" value="Enviar">

	</form>

	<br><br>

	<%= request.getAttribute("mensagemCodigo") %>
</body>
</html>