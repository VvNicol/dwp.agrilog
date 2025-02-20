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
	    http.csrf(csrf -> csrf.disable()) // Desactivar CSRF porque usamos JWT
	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin sesiones
	            .authorizeHttpRequests(auth -> auth
	                    .requestMatchers("/admin/**").hasRole("ADMIN") // Solo ADMIN puede acceder a /admin/**
	                    .requestMatchers("/usuario/**").hasRole("USUARIO") // Solo USUARIO puede acceder a /usuario/**
	                    .anyRequest().permitAll() // 🚀 Cualquier otra ruta es pública
	            )
	            .addFilterBefore(new JwtFiltro(), UsernamePasswordAuthenticationFilter.class); // Agregar filtro de JWT

	    return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
