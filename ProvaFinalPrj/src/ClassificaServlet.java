import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClassificaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ClassificaServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		request.setAttribute("mensagem", "");
		response.sendRedirect("classifica.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mensagem = "";
		String lista = request.getParameter("lista");
		Boolean descendente = request.getParameter("descendente") == null ? false : true;

		if (!lista.equals("")) {
			String[] arrayLista = lista.split("\\s+");
			for(String n : arrayLista) {
				try {
					Integer.parseInt(n);
				} catch (NumberFormatException numberFormatException) {
					mensagem = "A lista deve conter apenas n�meros inteiros.";
					break;
				} catch (Exception exception) {
					mensagem = "Lista inv�lida.";
					break;
				}
			}

			Integer[] arrayOrdenado = strArrayToIntArray(arrayLista);
			if (descendente)
				mensagem = ordenarArrayDesc(arrayOrdenado);
			else
				mensagem = ordenarArrayAsc(arrayOrdenado);
		} else {
			mensagem = "A lista n�o pode estar vazia.";
		}

		request.setAttribute("mensagem", mensagem);
		request.getRequestDispatcher("classifica.jsp").forward(request, response);
	}

	public static Integer[] strArrayToIntArray(String[] array){
		Integer[] aux = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			aux[i] = Integer.parseInt(array[i]);
		}

		return aux;
	}

	private static String ordenarArrayAsc(Integer[] vet) {
		String arrayOrdenado = "";

		Arrays.sort(vet);
		for(int v : vet)
			arrayOrdenado += v + " ";

		return arrayOrdenado;
	}

	private static String ordenarArrayDesc(Integer[] vet) {
		String arrayOrdenado = "";

		Arrays.sort(vet, Collections.reverseOrder());
		for(int v : vet)
			arrayOrdenado += v + " ";

		return arrayOrdenado;
	}
}