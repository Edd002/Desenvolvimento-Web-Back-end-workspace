

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OutroListener implements HttpSessionListener {

    public OutroListener() {
    }

    public void sessionCreated(HttpSessionEvent se)  {
    	System.out.println("Nova sessão criada");
    }

    public void sessionDestroyed(HttpSessionEvent se)  {
    	System.out.println("Sessão destruída");
    }
	
}
