<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Resultado</title>
</head>
<body>
	<h1>Resultado</h1>

	<h2><b>Nome Informado</b></h2><br>
	<label>Nome: <%= request.getAttribute("nomeInformado") %></label>
	<br><br>
	<h2><b>Nome Modificado</b></h2><br>
	<label>Nome: <%= request.getAttribute("nomeModificado") %></label>
	<br>
	<a href="entrada.jsp">Voltar</a>

	<br><br>

	<label>Data e hora: </label><%@ include file="data.jsp" %>
</body>
</html>