

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFilter implements Filter {
	
	// Tratamento dos par�metros de inicializa��o.
	// An�logo ao tratamento dos par�metros do servlet.
	// N�o pode ser alterado ap�s inicializado.
	private int parametro1 = 0;

    public MyFilter() {
    }

    // M�todo de ciclo de vida
    // Executado uma �nica vez antes da destrui��o final do filtro
    public void destroy() {
	}

	// M�todo de ciclo de vida
    // Executado a cada requisi��o interceptada
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	// Neste trecho de c�digo est� sendo feita a filtragem da requisi��o.
    	// Ele � executado antes do servlet receber a requisi��o.
    	System.out.println("Filtro: antes do servlet");
    	
    	// Type cast para recupera��o de sess�o dentro do filtro
    	//((HttpServletRequest)request).getSession();
    	
		// Passa a requisi��o para o pr�ximo elemento da cadeia
    	chain.doFilter(request, response);
    	
    	// Neste trecho de c�digo est� sendo feita a filtragem da resposta
    	// Ele � executado antes do cliente receber a resposta
    	System.out.println("Filtro: ap�s o servlet");
	}

	// M�todo de ciclo de vida
	// Executado uma �nica vez na inicializa��o do filtro
	public void init(FilterConfig fConfig) throws ServletException {
		// Tratamento dos par�metros de inicializa��o
		// Devem ser consistidos como na discuss�o dos servlets,
		// mas o professor ficou com pregui�a
		parametro1 = Integer.parseInt(fConfig.getInitParameter("parametro1"));
		System.out.println(parametro1);
	}

}
