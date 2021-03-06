<%@page import="javax.swing.JOptionPane"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Pot�ncia</title>

	<%!
		private double calcularPotencia(double x, int n) {
			if (n == 0)
				return 1.0;
	
			if (n < 0) 
				return 1.0 / calcularPotencia(x, -n);
	
			if (n % 2 == 0) {
				double res = calcularPotencia(x, n / 2);
				return res * res;
			}
	
			return x * calcularPotencia(x, n-1);
		}
	%>
</head>
<body>
	<%
		String resultado = "0";
		String mensagem = "";
		double base = 0;
		int expoente = 0;

		if ("POST".equalsIgnoreCase(request.getMethod())) {
			try {
				base = Double.parseDouble(request.getParameter("base"));
				expoente = Integer.parseInt(request.getParameter("expoente"));
				resultado = String.valueOf(calcularPotencia(base, expoente));
			} catch (NumberFormatException numberFormatException) {
				mensagem += "A base deve ser um n�mero real e o expoente um n�mero inteiro.<br>";
			} catch (Exception exception) {
				mensagem += "Erro ao informar dados.<br>";
			}
		}
	%>

	<h1>Pot�ncia</h1>
	<form action="potencia.jsp" method="post">
		<label>Base (n�mero real): </label> <input type="text" name="base"><br>
		<label>Expoente (n�mero inteiro): </label> <input type="text" name="expoente"><br>
		<input type="submit" value="Calcular"><br><br>
	</form>
	<label>Resultado: </label> <%= resultado %> <br>
	<a href="potencia.jsp">Reiniciar</a><br>
	<a href="login.jsp">Sair</a><br><br>
	<%= mensagem %>
</body>
</html>

