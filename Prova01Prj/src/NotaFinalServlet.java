import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class NotaFinalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final int MENOR_NOTA_APROVACAO = 50;
	private static final int MAIOR_NOTA_APROVACAO = 80;
	private static int notaAprovacao = 60;

	private static final int MENOR_PESO_SEGUNDA_PROVA = 1;
	private static final int MAIOR_PESO_SEGUNDA_PROVA = 4;
	private static int pesoSegundaProva = 2;
	
	public NotaFinalServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		try {
			int notaAprovacaoInit = Integer.parseInt(config.getInitParameter("notaAprovacao"));

			if (notaAprovacaoInit >= MENOR_NOTA_APROVACAO && notaAprovacaoInit <= MAIOR_NOTA_APROVACAO)
				notaAprovacao = notaAprovacaoInit;
		} catch (NumberFormatException numberFormatException) {
			// this.notaAprovacao = 60;
		} catch (Exception exception) {
			// this.notaAprovacao = 60;
		}

		try {
			int pesoSegundaProvaInit = Integer.parseInt(config.getInitParameter("pesoSegundaProva"));

			if (pesoSegundaProvaInit >= MENOR_PESO_SEGUNDA_PROVA && pesoSegundaProvaInit <= MAIOR_PESO_SEGUNDA_PROVA)
				pesoSegundaProva = pesoSegundaProvaInit;
		} catch (NumberFormatException numberFormatException) {
			// this.pesoSegundaProva = 2;
		} catch (Exception exception) {
			// this.pesoSegundaProva = 2;
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		exibir("", response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resposta = "";

		int primeiraProva = 0;
		int segundaProva = 0;
		boolean considerarEspecial = false;
		int especialProva = 0;
		double notaFinal = 0.0;

		try {
			primeiraProva = Integer.parseInt(request.getParameter("primeiraProva"));
			if (primeiraProva < 0 || primeiraProva > 100)
				resposta += "O valor da primeira prova deve estar entre 0 e 100.<br>";

			segundaProva = Integer.parseInt(request.getParameter("segundaProva"));
			if (segundaProva < 0 || segundaProva > 100)
				resposta += "O valor da segunda prova deve estar entre 0 e 100.<br>";

			if (!(primeiraProva < 0 || primeiraProva > 100) && !(segundaProva < 0 || segundaProva > 100)) {
				notaFinal = calcularNotaFinal(primeiraProva, segundaProva);
				resposta = "Nota final (sem especial): " + notaFinal + "<br>" + verificarAprovacao(notaFinal);
			}

			considerarEspecial = request.getParameter("considerarEspecial") != null;
			if (considerarEspecial) {
				especialProva = Integer.parseInt(request.getParameter("especialProva"));
				if (especialProva < 0 || especialProva > 100)
					resposta += "O valor da prova especial deve estar entre 0 e 100.<br>";

				if (!(primeiraProva < 0 || primeiraProva > 100) && !(segundaProva < 0 || segundaProva > 100) && !(especialProva < 0 || especialProva > 100)) {
					notaFinal = calcularNotaFinalEspecial(primeiraProva, segundaProva, especialProva);
					resposta = "Nota final (com especial): " + notaFinal + "<br>" + verificarAprovacao(notaFinal);
				}
			}
		} catch (NumberFormatException numberFormatException) {
			resposta = "Erro ao informar notas. Informe apenas valores inteiros.";
		} catch (Exception exception) {
			resposta = "Erro ao informar notas.";
		}

		exibir(resposta, response);
	}

	private static void exibir(String resposta, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Cálculo de Nota Final</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<form action='notafinalservlet' method='post'>");
		out.println("<h1>Cálculo de Nota Final</h1>");
		out.println("<br>");
		out.println("<label>Primeira prova: </label>");
		out.println("<input type='text' name='primeiraProva'>");
		out.println("<br>");
		out.println("<label>Segunda prova: </label>");
		out.println("<input type='text' name='segundaProva'>");
		out.println("<br>");
		out.println("<label>Considerar prova especial? </label>");
		out.println("<input type='checkbox' name='considerarEspecial'>");
		out.println("<br>");
		out.println("<label>Segunda prova: </label>");
		out.println("<input type='text' name='especialProva'>");
		out.println("<br>");
		out.println("<input type='submit' name='enviar' value='Enviar'>");
		out.println("</form>");
		out.println("<br>");
		out.println("<label>"+ resposta +"</label>");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}

	private static double calcularNotaFinal(int primeiraProva, int segundaProva) {
		return (double) ((primeiraProva + pesoSegundaProva * segundaProva)) / (pesoSegundaProva + 1);
	}

	private static double calcularNotaFinalEspecial(int primeiraProva, int segundaProva, int especialProva) {
		return (double) (calcularNotaFinal(primeiraProva, segundaProva) + especialProva) / 2;
	}

	private static String verificarAprovacao(double notaFinal) {
		if (notaFinal < notaAprovacao)
			return "Reprovado";
		else
			return "Aprovado";
	}
}
