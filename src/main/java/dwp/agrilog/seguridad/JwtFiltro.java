package dwp.agrilog.seguridad;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import dwp.agrilog.utilidades.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFiltro extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String requestURI = request.getRequestURI();

		if (requestURI.startsWith("/proyectoAgricola/html/inicio/iniciarSesion")
			|| requestURI.startsWith("/proyectoAgricola/html/inicio/registrarse")
			|| requestURI.startsWith("/proyectoAgricola/html/inicio/favicon")
			|| requestURI.startsWith("/proyectoAgricola/html/inicio/verificarCorreo")
			|| requestURI.startsWith("/proyectoAgricola/html")
			|| requestURI.startsWith("/proyectoAgricola/estilos")
			|| requestURI.startsWith("/proyectoAgricola/index")
			|| requestURI.startsWith("/proyectoAgricola/img")
			|| requestURI.startsWith("/proyectoAgricola/js")
			
			|| requestURI.startsWith("/proyectoAgricola/inicio/iniciar-sesion")
			|| requestURI.startsWith("/proyectoAgricola/inicio/registrarse")
			|| requestURI.startsWith("/proyectoAgricola/inicio/verificar-correo")
			) {
			chain.doFilter(request, response);
			return;
		}

		String token = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (token == null || !token.startsWith("Bearer ")) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("Acceso denegado: Token no encontrado.");
			return;
		}

		token = token.substring(7);

		try {
			Claims claims = JwtUtil.obtenerClaimsDesdeToken(token);
			String correo = claims.getSubject();
			String rol = claims.get("rol", String.class);

			if (correo != null && rol != null) {
				List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol));

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(correo,
						null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("Token inválido o expirado.");
			return;
		}

		chain.doFilter(request, response);
	}

}
