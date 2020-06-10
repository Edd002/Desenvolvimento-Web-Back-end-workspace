<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Erro</title>
</head>
<body>
	<h3>Servlet utilizado <%= request.getSession().getAttribute("contador") %> vezes.</h3>
	<h4>Limite alcançado para essa sessão.</h4><br>
	<a href="entrada.jsp">Voltar</a>
</body>
</html>