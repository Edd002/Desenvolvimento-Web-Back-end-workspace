

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndicadoresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private double k = 1.0;
	private double e = 2.0;
       
    public IndicadoresServlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		if("moderno".equals(config.getInitParameter("tipocalculo"))) {
			k = 1.3;
			e = 2.5;
		}
	}
	
	private double pesoIdeal(double h, boolean masculino) {
		double hc = h * 100;
		double s = masculino ? 4.0 : 2.0;
		
		return hc - 100 - (hc - 150) / s;
	}
	
	private double imc(double m, double h) {
		return k * m / Math.pow(h, e);
	}
	
	private String imcTab(double imc) {
		String result;
		
		if(imc <= 18.5)
			result = "Abaixo do peso";
		else if(imc <= 24.9)
			result = "Saudável";
		else if(imc <= 29.9)
			result = "Sobrepeso";
		else if(imc <= 34.9)
			result = "Obesidade grau I";
		else if(imc <= 39.9)
			result = "Obesidade grau II";
		else
			result = "Obesidade grau III";
		
		return result;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		exibe(response, "");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		double altura, peso;
		boolean masculino;
		String resposta = "";
		
		try {
			altura = Double.parseDouble(request.getParameter("altura"));
			if(altura <= 0.0)
				resposta = "Altura inválida";
			else {
				masculino = request.getParameter("masculino") != null;
				
				String p = request.getParameter("peso");
				if(p == null || p.trim().isEmpty()) {
					resposta = "Peso ideal: " + pesoIdeal(altura, masculino) + " kg.";
				}
				else {
					peso = Double.parseDouble(p);
					if(peso <= 0.0)
						resposta = "Peso inválido";
					else {
						double imc = imc(peso, altura);
						resposta = "Índice de massa corporal: " + imc + " (" + imcTab(imc) + ").";
					}
				}
			}
		}
		catch (NumberFormatException e) {
			resposta = "Favor informar apenas números";
		}
		
		exibe(response, resposta);
	}

	private void exibe(HttpServletResponse response, String msg) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		out.println("<html><head><title>");
		out.println("Indicadores de saúde");
		out.println("</title></head><body>");
		out.println("<h1>Cálculo de Indicadores de Saúde</h1>");
		out.println("<form method='post' />");
		out.println("Altura: <input type='text' name='altura'/> metros");
		out.println("<br>Peso: <input type='text' name='peso'/> kg");
		out.println("<br>Sexo masculino? <input type='checkbox' name='masculino'/>");
		out.println("<br><input type='submit'/>");
		out.println("</form>");
		out.println(msg);
		out.println("</body></html>");
		
		out.close();
	}
}
