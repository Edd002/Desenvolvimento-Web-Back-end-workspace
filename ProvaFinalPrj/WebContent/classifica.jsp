<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Classifica</title>
</head>
<body>
	<h1>Classifica</h1>
	<form method="post" action="classifica">

		<label>Lista: </label>
		<input type="text" name="lista">
		<br>
		<input type="checkbox" name="descendente"> <label>Descendente</label>
		<br><br>
		<input type="submit" value="Ordenar">

	</form>

	<br><br>

	<%= request.getAttribute("mensagem") %>
</body>
</html>