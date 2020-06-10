

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MontyListener implements HttpSessionListener, ServletContextListener {

    public MontyListener() {
    }

    public void sessionCreated(HttpSessionEvent ev)  {
    	HttpSession session = ev.getSession();
    	session.setAttribute("pontos", 0);
    }

    public void sessionDestroyed(HttpSessionEvent ev)  { 
    }

	@Override
	public void contextDestroyed(ServletContextEvent ev) {
	}

	@Override
	public void contextInitialized(ServletContextEvent ev) {
		ServletContext context = ev.getServletContext();
		context.setAttribute("recorde", 0);
	}
	
}
