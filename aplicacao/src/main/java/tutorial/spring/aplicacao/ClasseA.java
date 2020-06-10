package tutorial.spring.aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ClasseA {

	// Convenção de nomes
	/*
	@Autowired
	private InterfaceB classeX;

	@Autowired
	private InterfaceB classeY;
	*/
	
	@Autowired
	@Qualifier("classeX")
	private InterfaceB interfaceB;

	/*
	public ClasseA(InterfaceB interfaceB) {
		this.interfaceB = interfaceB;
	}
	*/

	public void execute() {
		interfaceB.metodoInterfaceB();
		/*
		classeX.metodoInterfaceB();
		classeY.metodoInterfaceB();
		*/
	}
}
