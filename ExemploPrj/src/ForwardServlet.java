

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ForwardServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Aten��o: redirecionamento forward n�o pode ocorrer se j� houver sido gerada
		// qualquer sa�da para o usu�rio.
//		PrintWriter out = response.getWriter();
//		out.println("Sa�da gerada pelo servlet forward");
//		out.flush();
		
		// ILUSTRA��O: passagem de informa��es entre componentes atrav�s de atributos de requisi��o
		request.setAttribute("redirecionado", true);
		
		// Redirecionamento tipo forward para outro componente.
		// A requisi��o � definitivamente desviada e n�o retorna ao servlet original.
		// Obten��o do objeto de redirecionamento
		RequestDispatcher rd = request.getRequestDispatcher("ex");
		// Redirecionamento da requisi��o
		rd.forward(request, response);
	}

}
