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

public class ServletMatricula extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String selectMatricula = "select * from matricula";
	private final String insertMatricula = "insert into matricula(numero, titulo, nota) values (?, ?, ?)";
	private final String updateMatricula = "update matricula set nota = ? where numero = ? and titulo = ?";
	private final String deleteMatricula = "delete from matricula where numero = ? and titulo = ?";
	
	public ServletMatricula() {
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
		int nota = 0;

		try {
			int numero = Integer.parseInt(request.getParameter("numero"));
			if (escolha != 2)
				nota = Integer.parseInt(request.getParameter("nota"));

			if (nota >= 0 && nota <= 100) {
				if (escolha == 1)
					mensagem = inserirMatricula(numero, titulo, nota);
				else if (escolha == 2)
					mensagem = excluirMatricula(numero, titulo);
				else
					mensagem = atualizarMatricula(numero, titulo, nota);
			} else {
				mensagem = "Informe uma nota v�lida.";
			}
		} catch (NumberFormatException numberFormatException) {
			mensagem = "Informe um n�mero e uma nota v�lida para matr�cula.";
		} catch (Exception exception) {
			mensagem = "Informe um n�mero e uma nota v�lida v�lido.";
		}

		exibe(request, response, mensagem);
	}

	private void exibe(HttpServletRequest request, HttpServletResponse response, String mensagem) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Matr�cula");
		out.println("</title></head><body>");
		out.println("<h1>Manuten��o de Matr�cula</h1>");
		out.println("<form action='matricula' method='post'/>");
		out.println("<label>N�mero do Aluno: </label> <input type='text' name='numero'/><br>");
		out.println("<label>T�tulo da Mat�ria: </label> <input type='text' name='titulo'/><br>");
		out.println("<label>Nota: </label> <input type='text' name='nota'/><br>");
		out.println("<select name='escolha'>");
		out.println("<option value='1'>Inserir</option>");
		out.println("<option value='2'>Excluir por N�mero do Aluno e T�tulo da Mat�ira</option>");
		out.println("<option value='3'>Atualizar Nota por N�mero do Aluno e T�tulo da Mat�ria</option>");
		out.println("</select><br><br>");
		out.println("<input type='submit' value='Efetuar Opera��o no Banco de Dados'/><br><br>");
		out.println("</form>");
		out.println("<a href='matricula'>Reiniciar P�gina</a><br>");
		out.println("<br><br>LISTA DE MATR�CULAS<br>");
		listarMatriculas(out);
		out.println("<br>" + mensagem);
		out.println("</body></html>");

		out.close();
	}

	private void listarMatriculas(PrintWriter out) {
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getConnection();
			ps = connection.prepareStatement(selectMatricula);
			ResultSet rs = ps.executeQuery();

			while(rs.next())
				out.println("N�mero do Aluno: " + rs.getString(1) + " - Mat�ria: " + rs.getString(2) + " - Nota: "+ rs.getString(3) + "<br>");
		} catch (SQLException e) {
			out.print("Erro de SQL: " + e.getMessage());
		} catch (NamingException e) {
			out.print("Erro na obten��o do contexto inicial: " + e.getMessage());
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

	private String inserirMatricula(int numeroAluno, String tituloMateria, int nota) {
		String mensagem = "Matr�cula inserida.";
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getConnection();
			ps = connection.prepareStatement(insertMatricula);
			ps.setInt(1, numeroAluno);
			ps.setString(2, tituloMateria);
			ps.setInt(3, nota);
			ps.execute();
		} catch (SQLException e) {
			mensagem = "Erro de SQL: " + e.getMessage();
		} catch (NamingException e) {
			mensagem = "Erro na obten��o do contexto inicial: " + e.getMessage();
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

	private String atualizarMatricula(int numeroAluno, String tituloMateria, int nota) {
		String mensagem = "Matr�cula atualizada.";
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getConnection();
			ps = connection.prepareStatement(updateMatricula);
			ps.setInt(1, nota);
			ps.setInt(2, numeroAluno);
			ps.setString(3, tituloMateria);
			ps.execute();
		} catch (SQLException e) {
			mensagem = "Erro de SQL: " + e.getMessage();
		} catch (NamingException e) {
			mensagem = "Erro na obten��o do contexto inicial: " + e.getMessage();
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

	private String excluirMatricula(int numeroAluno, String tituloMateria) {
		String mensagem = "Matr�cula exclu�da.";
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getConnection();
			ps = connection.prepareStatement(deleteMatricula);
			ps.setInt(1, numeroAluno);
			ps.setString(2, tituloMateria);
			ps.execute();
		} catch (SQLException e) {
			mensagem = "Erro de SQL: " + e.getMessage();
		} catch (NamingException e) {
			mensagem = "Erro na obten��o do contexto inicial: " + e.getMessage();
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
			throw new Exception("Datasource n�o encontrado");

		Connection connection = ds.getConnection();
		if(connection == null)
			throw new Exception("Conex�o nula");

		return connection;	
	}
}