import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class ServletMateria extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String selectMateria = "select * from materia";
	private final String insertMateria = "insert into materia(titulo, periodo) values (?, ?)";
	private final String updateMateria = "update materia set periodo = ? where titulo = ?";
	private final String deleteMateria = "delete from materia where titulo = ?";

	public ServletMateria() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		exibe(request, response, "");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mensagem = "";
		String titulo = request.getParameter("titulo");
		int escolha = Integer.parseInt(request.getParameter("escolha"));
		int periodo = 0;

		try {
			if (escolha != 2)
				periodo = Integer.parseInt(request.getParameter("periodo"));

			if (escolha == 1)
				mensagem = inserirMateria(titulo, periodo);
			else if (escolha == 2)
				mensagem = excluirMateria(titulo);
			else
				mensagem = atualizarMateria(titulo, periodo);
		} catch (NumberFormatException numberFormatException) {
			mensagem = "Informe um período válido para matéria.";
		} catch (Exception exception) {
			mensagem = "Informe um período válido.";
		}

		exibe(request, response, mensagem);
	}

	private void exibe(HttpServletRequest request, HttpServletResponse response, String mensagem) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Matéria");
		out.println("</title></head><body>");
		out.println("<h1>Manutenção de Matéria</h1>");
		out.println("<form action='materia' method='post'/>");
		out.println("<label>Título: </label> <input type='text' name='titulo'/><br>");
		out.println("<label>Período: </label> <input type='text' name='periodo'/><br>");
		out.println("<select name='escolha'>");
		out.println("<option value='1'>Inserir</option>");
		out.println("<option value='2'>Excluir por Título</option>");
		out.println("<option value='3'>Atualizar Período por Título</option>");
		out.println("</select><br><br>");
		out.println("<input type='submit' value='Efetuar Operação no Banco de Dados'/><br><br>");
		out.println("</form>");
		out.println("<a href='materia'>Reiniciar Página</a><br>");
		out.println("<br><br>LISTA DE MATÉRIAS<br>");
		listarMaterias(out);
		out.println("<br>" + mensagem);
		out.println("</body></html>");

		out.close();
	}

	private void listarMaterias(PrintWriter out) {
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getConnection();
			ps = connection.prepareStatement(selectMateria);
			ResultSet rs = ps.executeQuery();

			while(rs.next())
				out.println(rs.getString(1) + " - " + rs.getString(2) + "<br>");
		} catch (SQLException e) {
			out.print("Erro de SQL: " + e.getMessage());
		} catch (NamingException e) {
			out.print("Erro na obtenção do contexto inicial: " + e.getMessage());
		} catch (Exception exception) {
			out.print(exception.getMessage());
		} finally {
			try {
				if(ps != null)
					ps.close();
				if(connection != null)
					connection.close();
			} catch (Exception e) {
				out.print(e.getMessage());
			}
		}
	}

	private String inserirMateria(String tituloMateria, int periodoMateria) {
		String mensagem = "Matéria inserida.";
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getConnection();
			ps = connection.prepareStatement(insertMateria);
			ps.setString(1, tituloMateria);
			ps.setInt(2, periodoMateria);
			ps.execute();
		} catch (SQLException e) {
			mensagem = "Erro de SQL: " + e.getMessage();
		} catch (NamingException e) {
			mensagem = "Erro na obtenção do contexto inicial: " + e.getMessage();
		} catch (Exception exception) {
			mensagem = exception.getMessage();
		} finally {
			try {
				if(ps != null)
					ps.close();
				if(connection != null)
					connection.close();
			} catch (Exception e) {
				mensagem = e.getMessage();
			}
		}

		return mensagem;
	}

	private String atualizarMateria(String tituloMateria, int periodoMateria) {
		String mensagem = "Matéria atualizada.";
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getConnection();
			ps = connection.prepareStatement(updateMateria);
			ps.setInt(1, periodoMateria);
			ps.setString(2, tituloMateria);
			ps.execute();
		} catch (SQLException e) {
			mensagem = "Erro de SQL: " + e.getMessage();
		} catch (NamingException e) {
			mensagem = "Erro na obtenção do contexto inicial: " + e.getMessage();
		} catch (Exception exception) {
			mensagem = exception.getMessage();
		} finally {
			try {
				if(ps != null)
					ps.close();
				if(connection != null)
					connection.close();
			} catch (Exception e) {
				mensagem = e.getMessage();
			}
		}

		return mensagem;
	}

	private String excluirMateria(String tituloMateria) {
		String mensagem = "Matéria excluída.";
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getConnection();
			ps = connection.prepareStatement(deleteMateria);
			ps.setString(1, tituloMateria);
			ps.execute();
		} catch (SQLException e) {
			mensagem = "Erro de SQL: " + e.getMessage();
		} catch (NamingException e) {
			mensagem = "Erro na obtenção do contexto inicial: " + e.getMessage();
		} catch (Exception exception) {
			mensagem = exception.getMessage();
		} finally {
			try {
				if(ps != null)
					ps.close();
				if(connection != null)
					connection.close();
			} catch (Exception e) {
				mensagem = e.getMessage();
			}
		}

		return mensagem;
	}

	private Connection getConnection() throws NamingException, SQLException, Exception {
		InitialContext ctx = new InitialContext();

		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/xdb");
		if(ds == null)
			throw new Exception("Datasource não encontrado");

		Connection connection = ds.getConnection();
		if(connection == null)
			throw new Exception("Conexão nula");

		return connection;	
	}
}