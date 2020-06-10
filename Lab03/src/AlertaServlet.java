import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AlertaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AlertaServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		ServletContext context = getServletContext();

		int contadorUsuarios = 0;
		String mensagem = "";
		String cor = "";

		if(context.getAttribute("contadorUsuarios") == null)
    		context.setAttribute("contadorUsuarios", 0);
		else
			contadorUsuarios = (Integer) context.getAttribute("contadorUsuarios");

		if (session == null) {
			session = request.getSession();
			session.setAttribute("cores", 0);
			mensagem = "Alerta verde";
			cor = "green";

			context.setAttribute("contadorUsuarios", ++contadorUsuarios);
		} else {
			if (contadorUsuarios <= 2) {
				int sessionCor = (Integer) session.getAttribute("cores");

				if (sessionCor == 0) {
					session.setAttribute("cores", 1);
					mensagem = "Alerta verde";
					cor = "green";
				} else if (sessionCor == 1) {
					session.setAttribute("cores", 2);
					mensagem = "Alerta amarelo";
					cor = "yellow";
				} else if (sessionCor == 2) {
					session.setAttribute("cores", 0);
					mensagem = "Alerta vermelho";
					cor = "red";
				}
			} else {
				mensagem = "Número máximo de sessões atingido.";
				cor = "black";
			}
		}

		exibir(request, response, mensagem, cor);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	private void exibir(HttpServletRequest request, HttpServletResponse response, String mensagem, String cor) throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Alerta Servlet");
		out.println("</title></head><body>");
		out.println("<h1>Servlet de Alerta</h1>");
		out.println("<font color='" + cor + "'>" + mensagem + "</font>");

		out.println("</body></html>");

		out.close();
	}
}