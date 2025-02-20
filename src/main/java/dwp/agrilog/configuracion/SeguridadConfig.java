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
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import dwp.agrilog.seguridad.JwtFiltro;

@Configuration
@EnableWebSecurity
public class SeguridadConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/inicio/iniciar-sesion").permitAll()
	            .requestMatchers("/inicio/registrarse").permitAll()
	            .requestMatchers("/inicio/verificar-correo").permitAll()
	            .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
	            .requestMatchers("/usuario/**").hasAuthority("ROLE_USUARIO")
	            .anyRequest().permitAll()
	        )
	        .addFilterBefore(new JwtFiltro(), UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
	    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	    resolver.setPrefix("/WEB-INF/html/");
	    resolver.setSuffix(".jsp");
	    return resolver;
	}

	@Bean
    public HttpFirewall permitirBarrasDuplicadas() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true); // ✅ Permite "//" en las URLs
        return firewall;
    }

}
