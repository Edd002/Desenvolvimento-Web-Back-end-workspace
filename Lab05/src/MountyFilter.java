import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MountyFilter implements Filter {

	public static int maiorPontuacao = 0;

	public MountyFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		int pontuacaoUsuario = (int) session.getAttribute("pontuacaoUsuario");

		chain.doFilter(request, response);

		if (pontuacaoUsuario >= maiorPontuacao)
			maiorPontuacao = pontuacaoUsuario;
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}
}