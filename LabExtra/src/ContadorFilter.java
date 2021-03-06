import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ContadorFilter implements Filter {

	private int numeroMaximoUtilizacoes;

	public ContadorFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		int contador = (Integer) session.getAttribute("contador");

		if (contador > numeroMaximoUtilizacoes) {
			request.getRequestDispatcher("WEB-INF/erro.jsp").forward(request, response);
		} else {
			session.setAttribute("contador", ++contador);
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		try {
			numeroMaximoUtilizacoes = Integer.parseInt(fConfig.getInitParameter("numeroMaximoUtilizacoes"));
		} catch (NumberFormatException numberFormatException) {
			numeroMaximoUtilizacoes = 3;
		} catch (Exception exception) {
			numeroMaximoUtilizacoes = 3;
		}
	}
}