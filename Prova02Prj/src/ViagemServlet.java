import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ViagemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final double PRECO_COMBUSTIVEL_PADRAO = 1.0;
	private double precoGasolina;
	private double precoAlcool;
	private double precoDiesel;

	public ViagemServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		double precoGasolina = PRECO_COMBUSTIVEL_PADRAO;
		double precoAlcool = PRECO_COMBUSTIVEL_PADRAO;
		double precoDiesel = PRECO_COMBUSTIVEL_PADRAO;

		try {
			precoGasolina = Double.parseDouble(config.getInitParameter("precoGasolina"));
			precoAlcool = Double.parseDouble(config.getInitParameter("precoAlcool"));
			precoDiesel = Double.parseDouble(config.getInitParameter("precoDiesel"));
		} catch (NumberFormatException numberFormatException) {

		} catch (Exception exception) {

		}

		this.precoGasolina = precoGasolina;
		this.precoAlcool = precoAlcool;
		this.precoDiesel = precoDiesel;

		System.out.println("Gasolina: " + this.precoGasolina);
		System.out.println("Álcool: " + this.precoAlcool);
		System.out.println("Diesel: " + this.precoDiesel);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resposta = "";

		double distancia = 0.0;
		double velocidadeMedia = 0.0;
		boolean calculoConsumo = false;
		double consumoMedio = 0.0;
		String tipoCombustivel = null;

		try {
			distancia = Double.parseDouble(request.getParameter("distancia"));
			if (distancia <= 0)
				resposta += "A distância deve ser um número real positivo.<br>";

			velocidadeMedia = Double.parseDouble(request.getParameter("velocidade"));
			if (velocidadeMedia <= 0)
				resposta += "A velocidade média deve ser um número real positivo.<br>";

			if (distancia > 0 && velocidadeMedia > 0)
				resposta += "<br>Tempo de viagem estimado: " + tempoEstimadoViagem(distancia, velocidadeMedia) + "hrs<br>";

			calculoConsumo = request.getParameter("calcular") != null;
			if (calculoConsumo && distancia > 0) {
				consumoMedio = Double.parseDouble(request.getParameter("consumo"));
				if (consumoMedio <= 0)
					resposta += "O consumo médio deve ser um número real positivo.<br>";

				tipoCombustivel = request.getParameter("combustivel");
				if (tipoCombustivel.equals("Gasolina")) {
					resposta += "<br>Custo gasto com combustível(gasolina): R$" + custoCombustivel(distancia, consumoMedio, precoGasolina) + "<br>";
				} else if (tipoCombustivel.equals("Álcool")) {
					resposta += "<br>Custo gasto com combustível(álcool) R$" + custoCombustivel(distancia, consumoMedio, precoAlcool) + "<br>";
				} else if (tipoCombustivel.equals("Diesel")) {
					resposta += "<br>Custo gasto com combustível(diesel) R$" + custoCombustivel(distancia, consumoMedio, precoDiesel) + "<br>";
				}
			}

			System.out.println("\nDistância: " + distancia);
			System.out.println("Velocidade Média: " + velocidadeMedia);
			System.out.println("Calcular consumo? " + calculoConsumo);
			System.out.println("Consumo médio: " + consumoMedio);
			System.out.println("Tipo combustível: " + tipoCombustivel);
		} catch (NumberFormatException numberFormatException) {
			resposta = "Entrada inválida. Informe apenas números reais positivos.";
		} catch (Exception exception) {
			resposta = "Erro ao calcular.";
		}

		exibirResposta(response, resposta);
	}

	private double tempoEstimadoViagem(double distancia, double velocidadeMedia) {
		return distancia / velocidadeMedia;
	}

	private double custoCombustivel(double distancia, double consumoMedio, double precoCombustivel) {
		return consumoMedio * precoCombustivel * distancia;
	}

	private void exibirResposta(HttpServletResponse response, String resposta) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		out.println("<html><head><title>");
		out.println("Viagem");
		out.println("</title></head><body>");
		out.println("<h1>Viagem</h1>");
		out.println(resposta);
		out.println("<br><a href='viagem.html'>Voltar</a>");
		out.println("</body></html>");

		out.close();
	}














}