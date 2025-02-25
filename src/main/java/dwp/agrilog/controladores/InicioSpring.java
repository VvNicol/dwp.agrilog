package dwp.agrilog.controladores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

import dwp.agrilog.configuracion.DwpConfig;
import dwp.agrilog.configuracion.SeguridadConfig;

/**
 * Clase principal para iniciar la aplicación Spring Boot.
 * Excluye la configuración de seguridad predeterminada.
 * 
 * @autor nrojlla 25022025
 */
@SpringBootApplication(scanBasePackages = "dwp.agrilog", exclude = SecurityAutoConfiguration.class)
@Import({DwpConfig.class, SeguridadConfig.class})
@ServletComponentScan 
public class InicioSpring extends SpringBootServletInitializer {

	/**
	 * Configura la aplicación para despliegue en tomcat.
	 */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(InicioSpring.class);
    }
    
    /**
     * Método principal para ejecutar la aplicación Spring Boot.
     *
     * @param args 
     */
    public static void main(String[] args) {
        SpringApplication.run(InicioSpring.class, args);
    }
    
}
