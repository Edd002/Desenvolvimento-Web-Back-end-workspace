import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SerieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SerieServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		exibe(response, "");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ServletContext context = getServletContext();
		int sessionRequisicoes = (Integer) session.getAttribute("requisicoes");
		int termoCalculado = -1;
		int termoInformado = -1;
		String resposta = "";

		try {
			termoCalculado = calcularTermo(sessionRequisicoes + 1);
			termoInformado = Integer.parseInt(request.getParameter("termo"));

			if (termoInformado == termoCalculado) {
				session.setAttribute("acertos", (int) session.getAttribute("acertos") + 1);
				context.setAttribute("acertos", (int) context.getAttribute("acertos") + 1);
				resposta = "Termo correto. <br>Termo informado: " + termoInformado + "<br>Termo calculado: " + termoCalculado;
			} else {
				session.setAttribute("erros", (int) session.getAttribute("erros") + 1);
				context.setAttribute("erros", (int) context.getAttribute("erros") + 1);
				resposta = "Termo incorreto. <br>Termo informado: " + termoInformado + "<br>Termo calculado: " + termoCalculado;
			}

			resposta += "<br><br>Acertos do usuário atual: " + session.getAttribute("acertos") +
					"<br>Erros do usuário atual: " + session.getAttribute("erros") +
					"<br><br>Acertos de todos os usuários: " + context.getAttribute("acertos") +
					"<br>Erros de todos os usuários: " + context.getAttribute("erros") +
					"<br><br>Maior valor da série alcançado: " + SerieFilter.maiorValorSerie;

			session.setAttribute("requisicoes", ++sessionRequisicoes);
		} catch (NumberFormatException numberFormatException) {
			resposta = "Erro ao computar o termo informado. Informe apenas números inteiros.";
		} catch (Exception exception) {
			resposta = "Erro ao computar o termo informado. Informe apenas números inteiros.";
		}

		exibe(response, resposta);
	}

	public static int calcularTermo(int termo) {
		if (termo == 1)
			return 0;
		else if (termo == 2 || termo == 3)
			return 1;
		else
			return calcularTermo(termo - 1) + (2 * calcularTermo(termo - 3));
	}

	private void exibe(HttpServletResponse response, String msg) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Calcular Série");
		out.println("</title></head><body>");
		out.println("<h1>Calcular Série</h1>");
		out.println("<h3>Ao obter três erros a série é reiniciada juntamente com a quantidade de acertos e erros</h3>");
		out.println("<form action='serie' method='post'/>");
		out.println("<label>Próximo Termo da Série: </label>: <input type='text' name='termo'/>");
		out.println("<br><br><input type='submit' value='Enviar'/>");
		out.println("</form>");
		out.println("REGRAS:<br>"
				+ "1. O primeiro termo é 0 (s(1) = 0).<br>"
				+ "2. O segundo termo é 1 (s(2) = 1).<br>"
				+ "3. O terceiro termo é 1 (s(3) = 1).<br>"
				+ "4. Cada termo subsequente é calculado como s(n) = s(n - 1) + 2s(n - 3) (n > 3).<br><br>");
		out.println(msg);
		out.println("</body></html>");

		out.close();
	}
}