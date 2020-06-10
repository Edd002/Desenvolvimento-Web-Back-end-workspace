package tutorial.spring.aplicacao;

import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Qualifier("classeX")
//@Primary
public class ClasseX implements InterfaceB {

	@Override
	public void metodoInterfaceB() {
		System.out.println("Método na classe X");
	}

}
