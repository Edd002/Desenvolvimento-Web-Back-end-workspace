

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MountyFilter implements Filter {

    public MountyFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
		
		HttpSession session = ((HttpServletRequest)request).getSession();
		int pontos = (Integer)session.getAttribute("pontos");
		
		ServletContext context = session.getServletContext();
		int recorde = (Integer)context.getAttribute("recorde");
		
		if(pontos > recorde) {
			recorde = pontos;
			context.setAttribute("recorde", recorde);
		}
		
		PrintWriter out = response.getWriter();
		out.println("<p>Recorde: " + recorde);
		out.println("</body></html>");
		out.close();
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
