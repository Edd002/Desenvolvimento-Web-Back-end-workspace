

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
	
	// ATENÇÃO: erro. Não se pode guardar estado de usuário como
	// um atributo da classe servlet.
	//private int requisicoes = 0;
	
	// Parâmetro de inicialização
	// ATENÇÃO: NÃO PODE SER ALTERADO APÓS A INICIALIZAÇÃO.
	// UTILIZAR APENAS PARA LEITURA.
	private int maximo = 0;
       
    public ExemploServlet() {
        super();
    }

	// Método de inicialização
    // Executado uma única vez início do ciclo de vida do servlet
    // Atividade principal: leitura de parâmetros de inicialização
    public void init(ServletConfig config) throws ServletException {
    	// Começar sempre chamando o método sobreposto
    	super.init(config);
    	
    	// Obtenção dos parâmetros de inicialização
    	// Observação: todo parâmetro de inicialização tem tipo String e
    	//             deve ser convertido se necessário.
    	// ATENÇÃO: todo parâmetro de inicialização deve ser consistido
    	try {
    		maximo = Integer.parseInt(config.getInitParameter("maximo"));
    		if(maximo <= 0)
    			maximo = MAXIMO_DEFAULT;
    	}
    	catch (NumberFormatException e) {
    		// Opção 1 de tratamento: impedir o processamento do contexto
    		//throw new UnavailableException("Parâmetro 'máximo' incorreto");
    		
    		// Opção 2: utilizar um valor default
    		maximo = MAXIMO_DEFAULT;
    	}
    	
		System.out.println("Init executado: maximo = " + maximo);
	}

	// Método de finalização
    // Executado apenas uma vez no final do ciclo de vida antes da
    // destruição do servlet.
    public void destroy() {
		System.out.println("Destroy executado");
	}

	// Método de serviço
    // Executado a cada requisição recebida
//    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		// Recuperação de parâmetro de inicialização no método de serviço
//		// Atenção: deve ser consistido como no método init(), mas o professor ficou com preguiça.
//		// Obtenção do objeto de configuração
//		ServletConfig config = getServletConfig();
//		System.out.println("Service executado: maximo = " + maximo);
//		System.out.println("Service executado: minimo = " + config.getInitParameter("minimo"));
//		
//		if("GET".equals(request.getMethod())) {
//			System.out.println("Recebida requisição GET");
//		}
//		else if("POST".equals(request.getMethod())) {
//			System.out.println("Recebida requisição POST");
//		}
//		else {
//			System.out.println("Recebida requisição de outro tipo");
//		}
//	}
    
    // Método de serviço adequado para um tipo específico de requisição
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// Contagem das requisições enviadas por todos os usuários
    	// Recuperação da variável de contexto
    	ServletContext context = getServletContext();
    	// Verifica se o atributo já existe
    	// Funcionalidade de inicialização movida para o Listener
//    	if(context.getAttribute("requisicoes") == null) {
//    		// Atributo não existe; grava o valor inicial
//    		context.setAttribute("requisicoes", 0);
//    	}
    	
    	// Recuperação e atualização do atributo de contexto
    	int requisicoesGlobais = (Integer)context.getAttribute("requisicoes");
    	context.setAttribute("requisicoes", ++requisicoesGlobais);
    	
    	// Contagem de requisições enviadas por um usuário
    	// Verifica se o usuário já tem uma sessão
    	// Funcionalidade de inicialização movida para o Listener
//    	HttpSession session = request.getSession(false);
//    	if(session == null) {
//    		// Usuário não possui sessão; cria uma nova
//    		session = request.getSession();
//    		// Inicialização dos atributos da sessão
//    		session.setAttribute("requisicoes", 0);
//    	}
    	// Recupera e atualiza o atributo de sessão
    	HttpSession session = request.getSession();
    	int requisicoes = (Integer)session.getAttribute("requisicoes");
    	session.setAttribute("requisicoes", ++requisicoes);
    	
    	// ATIVIDADES TÍPICAS DE UM MÉTODO DE SERVIÇO
    	
    	String resposta = "";
    	
    	// Recuperação e consistência dos parâmetros da requisição
    	// Todo parâmetro da requisição é String e deve ser convertido caso necessário.
    	try {
    		int numero = Integer.parseInt(request.getParameter("numero"));
    		// Consistência de faixa de valores
    		if(numero > maximo)
    			resposta = "O número não pode ser maior que " + maximo;
    		else {
    			// Implementação do requisito a ser atendido pelo componente
    			resposta = numero + " é " + (par(numero) ? "par" : "ímpar");
    		}
    	} catch (NumberFormatException e) {
			// Consistência de formato
    		resposta = "Favor informar um número inteiro";
		}
    	
    	// Exibição da resposta para o usuário
    	exibe(request, response, resposta);
    }
    
    // Método para exibição de resposta para o usuário
    private void exibe (HttpServletRequest request, HttpServletResponse response, String resposta) throws IOException, ServletException {
    	
    	HttpSession session = request.getSession();
    	
    	PrintWriter out = response.getWriter();
    	response.setContentType("text/html");
    	
    	out.println("<html><head><title>");
    	out.println("Exemplo");
    	out.println("</title></head><body>");
    	
    	// Redirecionamento tipo include para recurso estático
    	// Obtenção do objeto de redirecionamento
    	RequestDispatcher rd = request.getRequestDispatcher("cabecalho.html");
    	// Redirecionamento da requisição
    	rd.include(request, response);	
    	
    	out.println("<h1>Servlet de exemplo</h1>");
    	out.println(resposta);
    	
    	// Exibição da contagem de requisições
    	out.println("<p>Requisições recebidas: " + session.getAttribute("requisicoes"));
    	out.println("<br>Requisições totais: " + getServletContext().getAttribute("requisicoes"));
    	
    	// ILUSTRAÇÃO: verifica se requisição foi redirecionada de outro componente
    	// Observação: o outro componente deve ter gravado um atributo de requisição
    	if(request.getAttribute("redirecionado") != null)
    		out.println("<p>Requisição redirecionada");
    	else
    		out.println("<p>Requisição não redirecionada");
    	
    	// Redirecionamento tipo include para recurso dinâmico
    	// Obtenção do objeto de redirecionamento
    	RequestDispatcher rd2 = request.getRequestDispatcher("rodape");
    	// Redirecionamento da requisição
    	rd2.include(request, response);
    	
    	out.println("</body></html>");
    	
    	out.close();
    }
    
    // Método de implementação de requisito
    private boolean par(int n) {
    	return n % 2 == 0;
    }

}
