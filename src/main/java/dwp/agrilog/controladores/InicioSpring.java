package dwp.agrilog.controladores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import agrilog.controladores.Inicio;

@SpringBootApplication(scanBasePackages = "dwp.agrilog", exclude = SecurityAutoConfiguration.class )
public class InicioSpring {
	
	public static void main(String[] args) {
		SpringApplication.run(Inicio.class, args);
	}

}
