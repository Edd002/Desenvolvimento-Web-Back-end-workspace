import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IMCServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	boolean tradicional;
	
    public IMCServlet() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		if (config.getInitParameter("tradicional").equals("true"))
			tradicional = true;
		else
			tradicional = false;
	}

	public void destroy() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String alturaS = request.getParameter("altura");
		String pesoS = request.getParameter("peso");
		String sexoS = request.getParameter("sexo");

		double altura;
		double peso;
		boolean masculino;

		try {
			altura = Double.parseDouble(alturaS);

			if (altura <= 0) {
				exibirSaida(response, "Altura deve ser positiva.");
				return;
			}

			if (sexoS == null)
				masculino = false;
			else
				masculino = true;

			if (pesoS.equals("")) {
				exibirSaida(response, "O peso ideal é: " + calcularPesoIdeal(altura, masculino));
			} else {
				peso = Double.parseDouble(pesoS);

				if (peso <= 0) {
					exibirSaida(response, "Peso deve ser positivo.");
					return;
				}

				exibirSaida(response, classificacaoIMC(altura, peso));
			}
		} catch (Exception exception) {
			exibirSaida(response, "Peso ou altura inválidos.");
		}
	}

	private double calcularPesoIdeal(double altura, boolean masculino) {
		double alturaCm = altura * 100;

		if (masculino)
			return alturaCm - 100 - ((alturaCm - 150) / 4.0);
		else
			return alturaCm - 100 - ((alturaCm - 150) / 2.0);
	}

	private String classificacaoIMC(double h, double m) {
		double k;
		double e;
		String classificacao;

		if (tradicional) {
			k = 1;
			e = 2;
		} else {
			k = 1.3;
			e = 2.5;
		}

		double imc = (k * m) / Math.pow(h, e);

		if (imc < 18.5)
			classificacao = "Abaixo do peso";
		else if (imc <= 24.9)
			classificacao = "Saudável";
		else if (imc <= 29.9)
			classificacao = "Sobrepeso";
		else if (imc <= 34.5)
			classificacao = "Obesidade grau I";
		else if (imc <= 39.9)
			classificacao = "Obesidade grau II (severa)";
		else
			classificacao = "Obesidade grau III (mórbida)";

		return classificacao;
	}

	private void exibirSaida(HttpServletResponse response, String saida) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println(
		"<html>"
		+"<head>"
			+"<meta charset='ISO-8859-1'>"
			+"<title>Primeira Avaliação</title>"
		+"</head>"
			+"<body>"
				+"<form action='imcservlet' method='post'>"
				+"	<h1>Cálculo de Indicadores de Saúde</h1>"
			
				+"	<label>Altura: </label>"
				+"	<input type='text' name='altura'>"
				+"	<label>metros</label>"
			
				+"	<br>"
			
				+"	<label>Peso: </label>"
				+"	<input type='text' name='peso'>"
				+"	<label>kg</label>"
			
				+"	<br>"
			
				+"	<label>Sexo masculino? </label>"
				+"	<input type='checkbox' name='sexo' value='masculino'><br>"
			
				+"	<br>"
			
				+"	<input type='submit' name='enviar' value='Enviar'>"
				+"</form>"

				+"<label>" + saida + "</label>"
			+"</body>"
		+"</html>");

		out.close();
	}
}