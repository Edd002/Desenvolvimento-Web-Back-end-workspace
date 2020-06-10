import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ContadorListener implements HttpSessionListener {

	public ContadorListener() {
	}

	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		session.setAttribute("contador", 0);
	}

	public void sessionDestroyed(HttpSessionEvent se) {
	}
}