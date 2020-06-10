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

public class ServletAluno extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String selectAluno = "select * from aluno";
	private final String insertAluno = "insert into aluno(numero, nome) values (?, ?)";
	private final String updateAluno = "update aluno set nome = ? where numero = ?";
	private final String deleteAluno = "delete from aluno where numero = ?";

	public ServletAluno() {
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
		String nome = request.getParameter("nome");
		int escolha = Integer.parseInt(request.getParameter("escolha"));

		try {
			int numero = Integer.parseInt(request.getParameter("numero"));

			if (escolha == 1)
				mensagem = inserirAluno(numero, nome);
			else if (escolha == 2)
				mensagem = excluirAluno(numero);
			else
				mensagem = atualizarAluno(numero, nome);
		} catch (NumberFormatException numberFormatException) {
			mensagem = "Informe um número válido para aluno.";
		} catch (Exception exception) {
			mensagem = "Informe um número válido.";
		}

		exibe(request, response, mensagem);
	}

	private void exibe(HttpServletRequest request, HttpServletResponse response, String mensagem) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Aluno");
		out.println("</title></head><body>");
		out.println("<h1>Manutenção de Aluno</h1>");
		out.println("<form action='aluno' method='post'/>");
		out.println("<label>Número: </label> <input type='text' name='numero'/><br>");
		out.println("<label>Nome: </label> <input type='text' name='nome'/><br>");
		out.println("<select name='escolha'>");
		out.println("<option value='1'>Inserir</option>");
		out.println("<option value='2'>Excluir por Número</option>");
		out.println("<option value='3'>Atualizar Nome por Número</option>");
		out.println("</select><br><br>");
		out.println("<input type='submit' value='Efetuar Operação no Banco de Dados'/><br><br>");
		out.println("</form>");
		out.println("<a href='aluno'>Reiniciar Página</a><br>");
		out.println("<br><br>LISTA DE ALUNOS<br>");
		listarAlunos(out);
		out.println("<br>" + mensagem);
		out.println("</body></html>");

		out.close();
	}

	private void listarAlunos(PrintWriter out) {
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getConnection();
			ps = connection.prepareStatement(selectAluno);
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

	private String inserirAluno(int numeroAluno, String nomeAluno) {
		String mensagem = "Aluno inserido.";
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getConnection();
			ps = connection.prepareStatement(insertAluno);
			ps.setInt(1, numeroAluno);
			ps.setString(2, nomeAluno);
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

	private String atualizarAluno(int numeroAluno, String nomeAluno) {
		String mensagem = "Aluno atualizado.";
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getConnection();
			ps = connection.prepareStatement(updateAluno);
			ps.setString(1, nomeAluno);
			ps.setInt(2, numeroAluno);
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

	private String excluirAluno(int numeroAluno) {
		String mensagem = "Aluno excluído.";
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getConnection();
			ps = connection.prepareStatement(deleteAluno);
			ps.setInt(1, numeroAluno);
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