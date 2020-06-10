

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExemploServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int MAXIMO_DEFAULT = 15;
	
	// ATEN��O: erro. N�o se pode guardar estado de usu�rio como
	// um atributo da classe servlet.
	//private int requisicoes = 0;
	
	// Par�metro de inicializa��o
	// ATEN��O: N�O PODE SER ALTERADO AP�S A INICIALIZA��O.
	// UTILIZAR APENAS PARA LEITURA.
	private int maximo = 0;
       
    public ExemploServlet() {
        super();
    }

	// M�todo de inicializa��o
    // Executado uma �nica vez in�cio do ciclo de vida do servlet
    // Atividade principal: leitura de par�metros de inicializa��o
    public void init(ServletConfig config) throws ServletException {
    	// Come�ar sempre chamando o m�todo sobreposto
    	super.init(config);
    	
    	// Obten��o dos par�metros de inicializa��o
    	// Observa��o: todo par�metro de inicializa��o tem tipo String e
    	//             deve ser convertido se necess�rio.
    	// ATEN��O: todo par�metro de inicializa��o deve ser consistido
    	try {
    		maximo = Integer.parseInt(config.getInitParameter("maximo"));
    		if(maximo <= 0)
    			maximo = MAXIMO_DEFAULT;
    	}
    	catch (NumberFormatException e) {
    		// Op��o 1 de tratamento: impedir o processamento do contexto
    		//throw new UnavailableException("Par�metro 'm�ximo' incorreto");
    		
    		// Op��o 2: utilizar um valor default
    		maximo = MAXIMO_DEFAULT;
    	}
    	
		System.out.println("Init executado: maximo = " + maximo);
	}

	// M�todo de finaliza��o
    // Executado apenas uma vez no final do ciclo de vida antes da
    // destrui��o do servlet.
    public void destroy() {
		System.out.println("Destroy executado");
	}

	// M�todo de servi�o
    // Executado a cada requisi��o recebida
//    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		// Recupera��o de par�metro de inicializa��o no m�todo de servi�o
//		// Aten��o: deve ser consistido como no m�todo init(), mas o professor ficou com pregui�a.
//		// Obten��o do objeto de configura��o
//		ServletConfig config = getServletConfig();
//		System.out.println("Service executado: maximo = " + maximo);
//		System.out.println("Service executado: minimo = " + config.getInitParameter("minimo"));
//		
//		if("GET".equals(request.getMethod())) {
//			System.out.println("Recebida requisi��o GET");
//		}
//		else if("POST".equals(request.getMethod())) {
//			System.out.println("Recebida requisi��o POST");
//		}
//		else {
//			System.out.println("Recebida requisi��o de outro tipo");
//		}
//	}
    
    // M�todo de servi�o adequado para um tipo espec�fico de requisi��o
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// Contagem das requisi��es enviadas por todos os usu�rios
    	// Recupera��o da vari�vel de contexto
    	ServletContext context = getServletContext();
    	// Verifica se o atributo j� existe
    	// Funcionalidade de inicializa��o movida para o Listener
//    	if(context.getAttribute("requisicoes") == null) {
//    		// Atributo n�o existe; grava o valor inicial
//    		context.setAttribute("requisicoes", 0);
//    	}
    	
    	// Recupera��o e atualiza��o do atributo de contexto
    	int requisicoesGlobais = (Integer)context.getAttribute("requisicoes");
    	context.setAttribute("requisicoes", ++requisicoesGlobais);
    	
    	// Contagem de requisi��es enviadas por um usu�rio
    	// Verifica se o usu�rio j� tem uma sess�o
    	// Funcionalidade de inicializa��o movida para o Listener
//    	HttpSession session = request.getSession(false);
//    	if(session == null) {
//    		// Usu�rio n�o possui sess�o; cria uma nova
//    		session = request.getSession();
//    		// Inicializa��o dos atributos da sess�o
//    		session.setAttribute("requisicoes", 0);
//    	}
    	// Recupera e atualiza o atributo de sess�o
    	HttpSession session = request.getSession();
    	int requisicoes = (Integer)session.getAttribute("requisicoes");
    	session.setAttribute("requisicoes", ++requisicoes);
    	
    	// ATIVIDADES T�PICAS DE UM M�TODO DE SERVI�O
    	
    	String resposta = "";
    	
    	// Recupera��o e consist�ncia dos par�metros da requisi��o
    	// Todo par�metro da requisi��o � String e deve ser convertido caso necess�rio.
    	try {
    		int numero = Integer.parseInt(request.getParameter("numero"));
    		// Consist�ncia de faixa de valores
    		if(numero > maximo)
    			resposta = "O n�mero n�o pode ser maior que " + maximo;
    		else {
    			// Implementa��o do requisito a ser atendido pelo componente
    			resposta = numero + " � " + (par(numero) ? "par" : "�mpar");
    		}
    	} catch (NumberFormatException e) {
			// Consist�ncia de formato
    		resposta = "Favor informar um n�mero inteiro";
		}
    	
    	// Exibi��o da resposta para o usu�rio
    	exibe(request, response, resposta);
    }
    
    // M�todo para exibi��o de resposta para o usu�rio
    private void exibe (HttpServletRequest request, HttpServletResponse response, String resposta) throws IOException, ServletException {
    	
    	HttpSession session = request.getSession();
    	
    	PrintWriter out = response.getWriter();
    	response.setContentType("text/html");
    	
    	out.println("<html><head><title>");
    	out.println("Exemplo");
    	out.println("</title></head><body>");
    	
    	// Redirecionamento tipo include para recurso est�tico
    	// Obten��o do objeto de redirecionamento
    	RequestDispatcher rd = request.getRequestDispatcher("cabecalho.html");
    	// Redirecionamento da requisi��o
    	rd.include(request, response);	
    	
    	out.println("<h1>Servlet de exemplo</h1>");
    	out.println(resposta);
    	
    	// Exibi��o da contagem de requisi��es
    	out.println("<p>Requisi��es recebidas: " + session.getAttribute("requisicoes"));
    	out.println("<br>Requisi��es totais: " + getServletContext().getAttribute("requisicoes"));
    	
    	// ILUSTRA��O: verifica se requisi��o foi redirecionada de outro componente
    	// Observa��o: o outro componente deve ter gravado um atributo de requisi��o
    	if(request.getAttribute("redirecionado") != null)
    		out.println("<p>Requisi��o redirecionada");
    	else
    		out.println("<p>Requisi��o n�o redirecionada");
    	
    	// Redirecionamento tipo include para recurso din�mico
    	// Obten��o do objeto de redirecionamento
    	RequestDispatcher rd2 = request.getRequestDispatcher("rodape");
    	// Redirecionamento da requisi��o
    	rd2.include(request, response);
    	
    	out.println("</body></html>");
    	
    	out.close();
    }
    
    // M�todo de implementa��o de requisito
    private boolean par(int n) {
    	return n % 2 == 0;
    }

}
