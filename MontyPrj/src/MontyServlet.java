

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MontyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MontyServlet() {
        super();
    }
    
    private void inicializa(HttpServletRequest request) {
    	Random r = new Random();
    	int premio = r.nextInt(3) + 1;
    	
    	String[] portas = {"1", "2", "3"};
    	
    	HttpSession session = request.getSession();
    	
    	session.setAttribute("premio", premio);
    	session.setAttribute("portas", portas);
    	session.setAttribute("estagio", 1);
    	
    	System.out.println("Prêmio: " + premio);
    }
    
    private void exibe(HttpServletRequest request, HttpServletResponse response, String msg) throws IOException {
    	
    	HttpSession session = request.getSession();
    	
    	PrintWriter out = response.getWriter();
    	response.setContentType("text/html");
    	
    	out.println("<html><head><title>");
    	out.println("Jogo de Monty Hall");
    	out.println("</title></head><body>");
    	out.println("<h1>Jogo de Monty Hall</h1>");
    	
    	out.println("Portas: ");
    	String[] portas = (String[])session.getAttribute("portas");
    	for(String porta: portas)
    		out.println(porta + " ");
    	
    	out.println("<form method='post'>");
    	out.println("Escolha: <input type='text' name='escolha'/>");
    	out.println("<br><input type='submit'/>");
    	out.println("</form>");
    	
    	out.println("<p>" + msg);
    	
    	out.println("<p>Pontuação: " + session.getAttribute("pontos"));
    	
    	out.println("<p><a href='monty'>Reiniciar</a>");
    	
//    	out.println("</body></html>");
//    	out.close();
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		inicializa(request);
		exibe(request, response, "");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		int estagio = (Integer)session.getAttribute("estagio");
		int premio = (Integer)session.getAttribute("premio");
		
		String resposta = "";
		
		try {
			int escolha = Integer.parseInt(request.getParameter("escolha"));
			if(escolha < 1 || escolha > 3)
				resposta = "A porta deve estar entre 1 e 3";
			else {
				if(estagio == 1) {
					// Tratamento do estágio 1
					int aberta = 0;
					for(int porta = 1; porta <= 3; porta++)
						if(porta != escolha && porta != premio) {
							aberta = porta;
							break;
						}
					session.setAttribute("aberta", aberta);
					String[] portas = (String[])session.getAttribute("portas");
					portas[aberta-1] = "*";
					session.setAttribute("portas", portas);
					session.setAttribute("estagio", 2);
				}
				else {
					// Tratamento do estágio 2
					int aberta = (Integer)session.getAttribute("aberta");
					int pontos = (Integer)session.getAttribute("pontos");
					
					if(escolha == aberta)
						resposta = "A porta " + escolha + " já está aberta";
					else {
						if(escolha == premio) {
							resposta = "Parabéns, você venceu";
							pontos += 10;
						}
						else {
							resposta = "Você perdeu";
							pontos /= 2;
						}
						session.setAttribute("pontos", pontos);
						inicializa(request);
					}
				}
			}
		}
		catch(NumberFormatException e) {
			resposta = "A porta deve ser um número inteiro entre 1 e 3";
		}
		
		exibe(request,response,resposta);
	}

}
