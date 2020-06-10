

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
		
		// Atenção: redirecionamento forward não pode ocorrer se já houver sido gerada
		// qualquer saída para o usuário.
//		PrintWriter out = response.getWriter();
//		out.println("Saída gerada pelo servlet forward");
//		out.flush();
		
		// ILUSTRAÇÃO: passagem de informações entre componentes através de atributos de requisição
		request.setAttribute("redirecionado", true);
		
		// Redirecionamento tipo forward para outro componente.
		// A requisição é definitivamente desviada e não retorna ao servlet original.
		// Obtenção do objeto de redirecionamento
		RequestDispatcher rd = request.getRequestDispatcher("ex");
		// Redirecionamento da requisição
		rd.forward(request, response);
	}

}
