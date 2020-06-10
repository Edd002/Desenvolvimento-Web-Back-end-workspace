import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MountyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MountyServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		exibe(response, "1 2 3", "0", "");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String padraoPortas = session.getAttribute("padraoPortas").toString();
		int portaPremio = (Integer) session.getAttribute("portaPremio");
		int pontuacaoUsuario = (Integer) session.getAttribute("pontuacaoUsuario");
		int escolhaUsuario = 0;
		int portaEliminar = 0;
		String mensagem = "";

		try {
			escolhaUsuario = Integer.parseInt(request.getParameter("escolha"));

			if (escolhaUsuario < 1 || escolhaUsuario > 3) {
				mensagem = "Informe apenas números entre 1 e 3.";
			} else {

				if (padraoPortas.equals("1 2 3")) {
					do {
						portaEliminar = (new Random()).nextInt(3) + 1;
					} while (portaEliminar == escolhaUsuario);

					mensagem = "Porta " + escolhaUsuario + " escolhida.";
					if (portaEliminar == 1) {
						padraoPortas = "* 2 3";
						mensagem += " Porta 1 eliminada.";
					} else if (portaEliminar == 2) {
						padraoPortas = "1 * 3";
						mensagem += " Porta 2 eliminada.";
					} else if (portaEliminar == 3) {
						padraoPortas = "1 2 *";
						mensagem += " Porta 3 eliminada.";
					}
				} else {

					if (escolhaUsuario == 1 && padraoPortas.equals("* 2 3")) {
						mensagem = "Porta 1 já escolhida";
					} else if (escolhaUsuario == 2 && padraoPortas.equals("1 * 3")) {
						mensagem = "Porta 2 já escolhida";
					} else if (escolhaUsuario == 3 && padraoPortas.equals("1 2 *")) {
						mensagem = "Porta 3 já escolhida";
					} else {

						if (escolhaUsuario == portaPremio) {
							pontuacaoUsuario += 10;
							mensagem = "Porta correta. Ganho de 10 pontos.";
						} else {
							pontuacaoUsuario /= 2;
							mensagem = "Porta incorreta. Perda de metade dos pontos.";
						}

						mensagem += "<br>Maior pontuação: " + MountyFilter.maiorPontuacao;
						padraoPortas = "1 2 3";
						session.setAttribute("portaPremio", (new Random()).nextInt(3) + 1);
						session.setAttribute("pontuacaoUsuario", pontuacaoUsuario);
					}
				}
			}
		} catch (NumberFormatException numberFormatException) {
			mensagem = "Erro ao informar porta. Informe apenas números entre 1 e 3.";
		} catch (Exception exception) {
			mensagem = "Erro ao informar porta.";
		}

		session.setAttribute("padraoPortas", padraoPortas);
		exibe(response, padraoPortas, String.valueOf(pontuacaoUsuario), mensagem);
	}

	private void exibe(HttpServletResponse response, String stringPortas, String pontuacaoUsuario, String mensagem) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Mounty Hall");
		out.println("</title></head><body>");
		out.println("<h1>Jogo de Mounty Hall</h1>");
		out.println("<form action='mounty' method='post'/>");
		out.println("<label>Portas: " + stringPortas + "</label><br>");
		out.println("<label>Escolha: </label> <input type='text' name='escolha'/>");
		out.println("<br><input type='submit' value='Enviar dados'/><br><br>");
		out.println("</form>");
		out.println("<label>Pontuação: </label>" + pontuacaoUsuario + "<br>");
		out.println("<a href='mounty'>Reiniciar</a><br><br>");
		out.println(mensagem);
		out.println("</body></html>");

		out.close();
	}
}