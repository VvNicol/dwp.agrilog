package dwp.agrilog.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dwp.agrilog.seguridad.JwtFiltro;

@Configuration
@EnableWebSecurity
public class SeguridadConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						auth -> auth
						
						.requestMatchers("/proyectoAgricola/inicio/iniciar-sesion").permitAll()
			            .requestMatchers("/proyectoAgricola/inicio/registrarse").permitAll()
			            .requestMatchers("/proyectoAgricola/inicio/verificar-correo").permitAll()

						.requestMatchers("/proyectoAgricola/admin/**").hasAuthority("ROLE_ADMIN")
						.requestMatchers("/proyectoAgricola/usuario/**").hasAuthority("ROLE_USUARIO")

						.anyRequest().permitAll() // Permite el acceso a cualquier otra ruta sin token
				).addFilterBefore(new JwtFiltro(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
