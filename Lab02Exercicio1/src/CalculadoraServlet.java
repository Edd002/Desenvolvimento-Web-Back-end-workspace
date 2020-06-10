import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CalculadoraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CalculadoraServlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
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

		mostrarResultado(response, resposta);
	}

	private void mostrarResultado(HttpServletResponse response, String resposta) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
    	out.println("Calculadora");
    	out.println("</title></head><body>");
    	out.println("<h1>Resultado</h1>");
    	out.println(resposta);
    	out.println("</body></html>");

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