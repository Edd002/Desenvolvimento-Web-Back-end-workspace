<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>C�lculo de Datas</title>
</head>
<body>
	<h1>C�lculo de datas</h1>

	<form method="post" action="data">
		<p>Informe a data de conclus�o: </p>
	
		<label>Dia: </label> <input type="text" name="dia"><br>
		<label>M�s: </label> <input type="text" name="mes"><br>
		<label>Dia: </label> <input type="text" name="ano"><br>
	
		<input type="submit" value="Submit Query">
	</form>

	<br>

	<%= request.getAttribute("mensagem") %>
</body>
</html>