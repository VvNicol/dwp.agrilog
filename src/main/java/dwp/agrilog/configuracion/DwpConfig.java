package dwp.agrilog.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración general de la aplicación.
 *
 * @autor nrojlla 25022025
 */
@Configuration
public class DwpConfig {
	
	/**
	 * Bean para realizar peticiones HTTP a servicios externos (API).
	 * 
	 * @return Instancia de RestTemplate
	 */
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

	/**
	 * Bean para encriptar contraseñas usando BCrypt.
	 * 
	 * @return Instancia de PasswordEncoder con BCrypt
	 */
	@Bean
	public PasswordEncoder contraseniaEncriptada() {
		return new BCryptPasswordEncoder();
	}
	
}
