package dwp.agrilog.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DwpConfig {
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

	@Bean
	public PasswordEncoder contraseniaEncriptada() {
		return new BCryptPasswordEncoder();
	}
	
}
