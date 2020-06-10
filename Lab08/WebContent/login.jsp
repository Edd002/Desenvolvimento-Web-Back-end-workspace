<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login</title>

	<%!
		private boolean autentificarLogin(String codigo, String senha) {
			return codigo.equalsIgnoreCase(senha) && !codigo.equals("") && !senha.equals("");
		}
	%>
</head>
<body>
	<%
	String mensagem = "";

	if ("POST".equalsIgnoreCase(request.getMethod())) {
		boolean loginValido = autentificarLogin(request.getParameter("codigo"), request.getParameter("senha"));

		if (loginValido) {
			request.getSession(); // Apenas criar a sessão
			response.sendRedirect("potencia");
		} else {
			mensagem += "Login inválido.<br>";
		}
	} else if ("GET".equalsIgnoreCase(request.getMethod())) {
		HttpSession session = request.getSession();
		session.invalidate();
	}
	%>
	<h1>Login</h1>
	<form action="login.jsp" method="post">
		<label>Código: </label> <input type="text" name="codigo"><br>
		<label>Senha: </label> <input type="text" name="senha"><br>
		<input type="submit" value="logar"><br><br>
	</form>
	<a href="login.jsp">Recarregar Página</a><br><br>
	<%= mensagem %>
</body>
</html>