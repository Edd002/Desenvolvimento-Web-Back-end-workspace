import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SerieFilter implements Filter {

	public static int maiorValorSerie = 0;

	public SerieFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();

		int termoInformado = -1;
		try {
			termoInformado = Integer.parseInt(request.getParameter("termo"));
		} catch (NumberFormatException numberFormatException) {
			// termoInformado = -1;
		} catch (Exception exception) {
			// termoInformado = -1;
		}

		if ((int) session.getAttribute("erros") >= 3) {
			session.setAttribute("requisicoes", 0);
			session.setAttribute("acertos", 0);
			session.setAttribute("erros", 0);
		}

		int valorSerieAtual = SerieServlet.calcularTermo((int) session.getAttribute("requisicoes") + 1);
		if (valorSerieAtual > maiorValorSerie && termoInformado == valorSerieAtual)
			maiorValorSerie = valorSerieAtual;

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}