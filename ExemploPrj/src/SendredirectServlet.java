

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SendredirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SendredirectServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Ilustração: grava um atributo de requisição
		request.setAttribute("redirecionado", true);

		// Redirecionamento tipo Sendredirect (externo)
		// Solicita que o cliente envie outra requisição para um novo endereço
		response.sendRedirect("ex");
	}

}
