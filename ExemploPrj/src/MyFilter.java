

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFilter implements Filter {
	
	// Tratamento dos parâmetros de inicialização.
	// Análogo ao tratamento dos parâmetros do servlet.
	// Não pode ser alterado após inicializado.
	private int parametro1 = 0;

    public MyFilter() {
    }

    // Método de ciclo de vida
    // Executado uma única vez antes da destruição final do filtro
    public void destroy() {
	}

	// Método de ciclo de vida
    // Executado a cada requisição interceptada
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	// Neste trecho de código está sendo feita a filtragem da requisição.
    	// Ele é executado antes do servlet receber a requisição.
    	System.out.println("Filtro: antes do servlet");
    	
    	// Type cast para recuperação de sessão dentro do filtro
    	//((HttpServletRequest)request).getSession();
    	
		// Passa a requisição para o próximo elemento da cadeia
    	chain.doFilter(request, response);
    	
    	// Neste trecho de código está sendo feita a filtragem da resposta
    	// Ele é executado antes do cliente receber a resposta
    	System.out.println("Filtro: após o servlet");
	}

	// Método de ciclo de vida
	// Executado uma única vez na inicialização do filtro
	public void init(FilterConfig fConfig) throws ServletException {
		// Tratamento dos parâmetros de inicialização
		// Devem ser consistidos como na discussão dos servlets,
		// mas o professor ficou com preguiça
		parametro1 = Integer.parseInt(fConfig.getInitParameter("parametro1"));
		System.out.println(parametro1);
	}

}
