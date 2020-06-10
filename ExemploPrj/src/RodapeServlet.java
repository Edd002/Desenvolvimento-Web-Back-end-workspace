

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RodapeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RodapeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Gera��o de rodap� din�mico para inclus�o (com redirecionamento)
		// na sa�da de outro servlet.
		
		PrintWriter out = response.getWriter();
		out.println("<hr>" + new Date());
	}

}
