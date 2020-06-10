import java.util.Random;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MountyListener implements ServletContextListener, HttpSessionListener {

	public MountyListener() {
    }

	@Override
	public void sessionCreated(HttpSessionEvent se)  {
		HttpSession session = se.getSession();
		session.setAttribute("portaPremio", (new Random()).nextInt(3) + 1);
		session.setAttribute("padraoPortas", "1 2 3");
		session.setAttribute("pontuacaoUsuario", 0);
		session.setAttribute("maiorPontuacao", 0);
    }

	@Override
	public void sessionDestroyed(HttpSessionEvent se)  {
    }

	@Override
	public void contextDestroyed(ServletContextEvent sce)  {
    }

	@Override
	public void contextInitialized(ServletContextEvent sce)  {
		//ServletContext context = sce.getServletContext();
    	//context.setAttribute("maiorPontuacao", 0);
    }
}