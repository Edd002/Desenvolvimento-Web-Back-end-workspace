<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
String nome = request.getParameter("nome");
if(nome.trim().isEmpty())
  nome = "World";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hello JSP</title>
</head>
<body>

	<h1>Hello JSP</h1>
	Hello <%= nome %>
	
	<br><a href="hello.html">Voltar</a>

</body>
</html>