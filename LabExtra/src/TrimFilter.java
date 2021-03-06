import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class TrimFilter implements Filter {

	private boolean upperCase = false;

	public TrimFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		int tamNomeInformado = (request.getParameter("nome") + request.getParameter("sobrenome")).length();
		int tamNomeModificado = (request.getParameter("nome").trim() + request.getParameter("sobrenome").trim()).length();

		String nomeInformado = request.getParameter("nome").trim() + " " + request.getParameter("sobrenome").trim();
		String nomeModificado = nomeInformado;
		if (upperCase)
			nomeModificado = nomeInformado.toUpperCase();

		request.setAttribute("nomeInformado", nomeInformado + " (tamanho: " + tamNomeInformado + ")");
		request.setAttribute("nomeModificado", nomeModificado + " (tamanho: " + tamNomeModificado + ")");

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		if (fConfig.getInitParameter("init").equalsIgnoreCase("upper"))
			upperCase = true;
	}
}