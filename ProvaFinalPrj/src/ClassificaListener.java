import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ClassificaListener implements HttpSessionListener {

	public ClassificaListener() {
    }

	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		session.setAttribute("codigoVerificador", "");
		session.setAttribute("contadorClassificacoes", 0);
    }

	public void sessionDestroyed(HttpSessionEvent se) {
    }
}