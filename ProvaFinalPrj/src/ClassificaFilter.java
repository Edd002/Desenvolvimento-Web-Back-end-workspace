import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ClassificaFilter implements Filter {

	public ClassificaFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*
		if ("POST".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
			HttpSession session = ((HttpServletRequest) request).getSession();
			String codigoVerificador = (String) session.getAttribute("codigoVerificador");
			Integer contadorClassificacoes = (Integer) session.getAttribute("contadorClassificacoes");

			if (!codigoVerificador.equals("")) {
				if (contadorClassificacoes >= 5)
					session.invalidate();

				session.setAttribute("contadorClassificacoes", ++contadorClassificacoes);
				chain.doFilter(request, response);
			} else {
				((HttpServletRequest) request).setAttribute("mensagem", "Campo de c�digo inv�lido.");
			}
		}
		*/

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
