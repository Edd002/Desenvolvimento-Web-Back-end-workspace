

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class TesteDbServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final String selectAluno = "select * from aluno";

	public TesteDbServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<html><head><title>");
		out.println("Teste DB");
		out.println("</title></head><body>");
		out.println("<h1>Teste DB</h1>");
		
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getConnection();
			
			ps = connection.prepareStatement(selectAluno);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				out.println(rs.getString(1) + "/" + rs.getString(2) + "<br>");
			
		} catch (SQLException e) {
			out.print("Erro de SQL: " + e.getMessage());
		} catch (NamingException e) {
            out.print("Erro na obtenção do contexto inicial: " + e.getMessage());
		} catch (MyException e) {
			out.print(e.getMessage());
		} finally {
			try {
				if(ps != null) ps.close();
				if(connection != null) connection.close();
			} catch (Exception e) {
				out.print(e.getMessage());
			}
		}
		
		out.println("</body></html>");
	}
	
	private Connection getConnection() throws NamingException,  SQLException, MyException {
		
		InitialContext ctx = new InitialContext();
		
		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/xdb");
		if(ds == null)
			throw new MyException("Datasource não encontrado");
		
		Connection connection = ds.getConnection();
		if(connection == null)
			throw new MyException("Conexão nula");
		
		return connection;	
	}


}
