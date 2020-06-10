import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class AlertaListener implements HttpSessionListener, ServletContextListener {

	final private static AtomicInteger contadorSessoes = new AtomicInteger(0);

	public static int getContadorSessoes() {
		return contadorSessoes.get();
	}

	public AlertaListener() {
    }

	@Override
	public void sessionCreated(HttpSessionEvent se)  {
		HttpSession session = se.getSession();
		session.setAttribute("cores", 0);
		contadorSessoes.incrementAndGet();
    }

	@Override
	public void sessionDestroyed(HttpSessionEvent se)  {
		if(contadorSessoes.get() > 0)
			contadorSessoes.decrementAndGet();
    }

	@Override
	public void contextDestroyed(ServletContextEvent sce)  {
    }

	@Override
	public void contextInitialized(ServletContextEvent sce)  {
    }
}