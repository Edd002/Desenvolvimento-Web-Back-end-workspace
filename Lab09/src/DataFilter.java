import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class DataFilter implements Filter {

	private double limite;

	public DataFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		int valorAleatorio = (int) (Math.random() * 30);
		double probabilidadeIntercepta = (double) (Math.random() * 1);

		//System.out.println(probabilidadeIntercepta);
		if ("POST".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
			if (probabilidadeIntercepta > limite) {
				request.setAttribute("mensagem", "A diferen�a entre as datas � (valor aleat�rio): " + valorAleatorio + " dia(s).");
				request.getRequestDispatcher("WEB-INF/data.jsp").forward(request, response);
				return;
			}
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		try {
			limite = Double.parseDouble(fConfig.getInitParameter("limite"));
		} catch (NumberFormatException numberFormatException) {
			limite = 0.5;
		} catch (Exception exception) {
			limite = 0.5;
		}
	}
}