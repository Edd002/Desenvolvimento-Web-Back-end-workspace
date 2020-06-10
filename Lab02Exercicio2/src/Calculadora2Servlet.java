import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Calculadora2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Calculadora2Servlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		exibicao("Calculadora 2", "0", response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resposta = null;
		double operando1 = 0;
		double operando2 = 0;
		char operador = '\0';

		try {
			operando1 = Double.parseDouble(request.getParameter("operando1"));
			operando2 = Double.parseDouble(request.getParameter("operando2"));
			operador = request.getParameter("operador").charAt(0);

			resposta = efetuarOperacao(operando1, operando2, operador);
		} catch(NumberFormatException numberFormatException) {
			resposta = "Informe apenas números para efetuar uma operação.";
		} catch (Exception exception) {
			resposta = "Erro ao efetuar operação.";
		}

		exibicao("Calculadora", resposta, response);
	}

	private void exibicao(String titulo, String resposta, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Calculadora 2");
		out.println("</title></head><body>");
		out.println("<h1>" + titulo + "</h1>");
		out.println("<form action=\"calculadora2\" method=\"post\">");
		out.println("<input type=\"text\" name=\"operando1\">");
		out.println("<select name=\"operador\">");
		out.println("<option>+</option><option>-</option><option>*</option><option>/</option>");
		out.println("</select>");
		out.println("<input type=\"text\" name=\"operando2\">");
		out.println("<input type=\"submit\">");
		out.println("</form></body></html>");

		out.println("<br>Resultado: " + resposta);

		out.close();
	}

	private String efetuarOperacao(double operando1, double operando2, char operador) {
		if (operando2 == 0 && operador == '/')
			return "Divisão por 0 não permita.";

		String resultado = null;
		if (operador == '+')
			resultado = Double.toString(operando1 + operando2);
		else if (operador == '-')
			resultado = Double.toString(operando1 - operando2);
		else if (operador == '*')
			resultado = Double.toString(operando1 * operando2);
		else
			resultado = Double.toString(operando1 / operando2);

		return resultado;
	}
}