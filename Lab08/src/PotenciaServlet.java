import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PotenciaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PotenciaServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		exibe(request, response, "0", "");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultado = "0";
		String mensagem = "";
		double base = 0;
		int expoente = 0;

		try {
			base = Double.parseDouble(request.getParameter("base"));
			expoente = Integer.parseInt(request.getParameter("expoente"));
			resultado = String.valueOf(calcularPotencia(base, expoente));
		} catch (NumberFormatException numberFormatException) {
			mensagem += "A base deve ser um número real e o expoente um número inteiro.<br>";
		} catch (Exception exception) {
			mensagem += "Erro ao informar dados.<br>";
		}

		exibe(request, response, resultado, mensagem);
	}

	private void exibe(HttpServletRequest request, HttpServletResponse response, String resultado, String mensagem) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Potência");
		out.println("</title></head><body>");
		out.println("<h1>Potência</h1>");
		out.println("<form action='potencia' method='post'>");
		out.println("<label>Base (número real): </label> <input type='text' name='base'/><br>");
		out.println("<label>Expoente (número inteiro): </label> <input type='text' name='expoente'/><br>");
		out.println("<input type='submit' value='Calcular'/><br><br>");
		out.println("</form>");
		out.println("<label>Resultado: </label>" + resultado + "<br>");
		out.println("<a href='potencia'>Reiniciar</a><br>");
		out.println("<a href='login'>Sair</a><br><br>");
		out.println(mensagem);
		out.println("</body></html>");

		out.close();
	}

	/*
	private double calcularPotencia(double x, int n) {
		if (n < 0)
			return (double) 1 / Math.pow(x, -n);

		if (n > 0 && n % 2 == 0)
			return Math.pow(Math.pow(x, (double) n/2), 2);

		if (n > 0 && n % 2 != 0)
			return Math.pow(x, n - 1) * x;

		return 0;
	}
	 */

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
}
