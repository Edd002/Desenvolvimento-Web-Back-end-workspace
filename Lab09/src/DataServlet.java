import java.io.IOException;
import java.util.GregorianCalendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DataServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("mensagem", "");
		request.getRequestDispatcher("WEB-INF/data.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int dia = 0;
		int mes = 0;
		int ano = 0;
		int diferencaDatas = 0;
		String mensagem = "";

		try {
			dia = Integer.parseInt(request.getParameter("dia"));
			mes = Integer.parseInt(request.getParameter("mes"));
			ano = Integer.parseInt(request.getParameter("ano"));

			if (CalculoDatas.valida(dia, mes, ano)) {
				GregorianCalendar dataInformada = new GregorianCalendar(ano, mes, dia);
				GregorianCalendar dataAtual = new GregorianCalendar();
				diferencaDatas = CalculoDatas.diferenca(dataInformada, dataAtual);

				if (diferencaDatas > 0)
					mensagem = "A diferen�a entre as datas �: " + diferencaDatas + " dia(s).";
				else
					mensagem = "A data de entrada deve ser maior que a data atual.";
			}
		} catch (NumberFormatException numberFormatException) {
			mensagem = "Data inv�lida. Erro ao informar dia, m�s ou ano. Informe apenas n�meros inteiros.";
		} catch (Exception exception) {
			mensagem = "Erro ao informar dados.";
		}

		request.setAttribute("mensagem", mensagem);
		request.getRequestDispatcher("WEB-INF/data.jsp").forward(request, response);
	}
}