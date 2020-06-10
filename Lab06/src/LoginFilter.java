import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	public LoginFilter() {
	}

	@Override
	public void destroy() {
	}

	// Caso mandar uma requisição diretamente para PotenciaServlet, redirecionar para LoginServlet
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		if (session == null) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("login");
			requestDispatcher.forward(request, response);
		} else {
			chain.doFilter(request,response);
		}
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}
}