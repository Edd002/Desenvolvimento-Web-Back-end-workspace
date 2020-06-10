

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {

    public MyListener() {
    }

    public void contextDestroyed(ServletContextEvent sce)  {
    	System.out.println("Contexto destruído");
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	System.out.println("Contexto inicializado");
    }
	
}
