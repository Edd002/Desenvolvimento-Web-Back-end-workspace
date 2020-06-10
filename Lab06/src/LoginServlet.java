import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
        super();
    }

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();

		exibe(request, response, "");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mensagem = "";
		boolean loginValido = autentificarLogin(request.getParameter("codigo"), request.getParameter("senha"));

		if (loginValido) {
			request.getSession(); // Apenas criar a sessão
			response.sendRedirect("potencia");
		} else {
			mensagem += "Login inválido.<br>";
		}

		exibe(request, response, mensagem);
	}

	private void exibe(HttpServletRequest request, HttpServletResponse response, String mensagem) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Login");
		out.println("</title></head><body>");
		out.println("<h1>Login</h1>");
		out.println("<form action='login' method='post'/>");
		out.println("<label>Código: </label> <input type='text' name='codigo'/><br>");
		out.println("<label>Senha: </label> <input type='text' name='senha'/><br>");
		out.println("<input type='submit' value='Logar'/><br><br>");
		out.println("</form>");
		out.println("<a href='login'>Recarregar Página</a><br><br>");
		out.println(mensagem);
		out.println("</body></html>");

		out.close();
	}

	private boolean autentificarLogin(String codigo, String senha) {
		return codigo.equalsIgnoreCase(senha) && !codigo.equals("") && !senha.equals("");
	}
}