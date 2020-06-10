

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HelloWorldServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		if(nome.trim().isEmpty())
			nome = "World";
		
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>");
		out.println("Hello World");
		out.println("</title></head><body>");
		out.println("<h1>Hello World</h1>");
		out.println("Hello " + nome);
		out.println("<br><a href='hello.html'>Voltar</a>");
		out.println("</body></html>");
	}

}
