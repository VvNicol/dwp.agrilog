package dwp.agrilog.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Configuración para la seguridad de la aplicación. Define las reglas de
 * acceso, autenticación y administración de sesiones.
 * 
 * @autor nrojlla 04032025
 */
@Configuration
@EnableWebSecurity
public class SeguridadConfig {

	/**
	 * Configura la cadena de filtros de seguridad de Spring Security.
	 * 
	 * @param http Configuración de seguridad de HTTP.
	 * @return SecurityFilterChain con las reglas de seguridad aplicadas.
	 * @throws Exception Si ocurre algún error en la configuración.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
				.requestMatchers("/regis/**", "/form/**", "/inicio/**", "/estilos/**", "/img/**", "/js/**",
						"/favicon.ico")
				.permitAll().requestMatchers("/WEB-INF/jsp/inicio/**", "/WEB-INF/jsp/errores/**").permitAll()
				.requestMatchers("/usuario/**").hasAuthority("USUARIO").requestMatchers("/admin/**")
				.hasAuthority("ADMIN").anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

		return http.build();
	}

	/**
	 * Configura el repositorio del contexto de seguridad para almacenar información
	 * en la sesión del usuario.
	 * 
	 * @return SecurityContextRepository para administrar el contexto de
	 *         autenticación en la sesión.
	 */
	@Bean
	public SecurityContextRepository securityContextRepository() {
		return new HttpSessionSecurityContextRepository();
	}

	/**
	 * Configura el AuthenticationManager, encargado de la autenticación de los
	 * usuarios.
	 * 
	 * @param authenticationConfiguration Configuración de autenticación de Spring
	 *                                    Security.
	 * @return AuthenticationManager que gestiona la autenticación de usuarios.
	 * @throws Exception Si hay un error en la configuración.
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	/**
	 * Configura el resolver de vistas para permitir la correcta carga de archivos
	 * JSP.
	 * 
	 * @return InternalResourceViewResolver con la configuración de las vistas.
	 */
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

}
