package tutorial.spring.aplicacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AplicacaoApplication {

	public static void main(String[] args) {
		// Capturar contexto da aplicação
		ApplicationContext applicationContext = SpringApplication.run(AplicacaoApplication.class, args);
		ClasseA objetoA = applicationContext.getBean(ClasseA.class);

		//Forma tradicional (sem o Spring)
		//ClasseA objetoA = new ClasseA(new ClasseY());

		objetoA.execute();
	}

}
