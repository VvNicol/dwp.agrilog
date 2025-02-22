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
import jakarta.servlet.http.HttpSession;

public class JwtFiltro extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	        throws ServletException, IOException {

	    String requestURI = request.getRequestURI();

	    // Rutas públicas que no requieren autenticación
	    if (requestURI.startsWith("/proyectoAgricola/inicio/iniciar-sesion")
	    		|| requestURI.startsWith("/proyectoAgricola/inicio/")
	            || requestURI.startsWith("/proyectoAgricola/inicio/registrarse")
	            || requestURI.startsWith("/proyectoAgricola/inicio/verificar-correo")
	            || requestURI.startsWith("/proyectoAgricola/estilos")
	            || requestURI.startsWith("/proyectoAgricola/index")
	            || requestURI.startsWith("/proyectoAgricola/img")
	            || requestURI.startsWith("/proyectoAgricola/js")) {
	        chain.doFilter(request, response);
	        return;
	    }

	 // 🔥 Verificar si hay una sesión activa
	    HttpSession session = request.getSession(false);
	    if (session != null && session.getAttribute("usuario") != null) {
	        System.out.println("✅ Usuario autenticado por sesión: " + session.getAttribute("usuario"));
	        chain.doFilter(request, response);
	        return;
	    }

	    // 🔥 Si no hay sesión, verificar si hay un token JWT en la cabecera Authorization
	    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
	    if (token != null && token.startsWith("Bearer ")) {
	        token = token.substring(7); // Remover "Bearer " del token
	        try {
	            Claims claims = JwtUtil.obtenerClaimsDesdeToken(token);
	            String correo = claims.getSubject();
	            String rol = "ROLE_" + claims.get("rol", String.class); // 🔥 Agregar ROLE_

	            if (correo != null && rol != null) {
	                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(rol));
	                UsernamePasswordAuthenticationToken authentication =
	                        new UsernamePasswordAuthenticationToken(correo, null, authorities);
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	                System.out.println("✅ Usuario autenticado por JWT: " + correo + " con rol " + rol);
	                chain.doFilter(request, response);
	                return;
	            }
	        } catch (Exception e) {
	            System.out.println("❌ Token inválido: " + e.getMessage());
	            response.setStatus(HttpStatus.UNAUTHORIZED.value());
	            response.getWriter().write("Token inválido o expirado.");
	            return;
	        }
	    }

	    // ❌ Si no hay sesión ni token válido, denegar acceso
	    System.out.println("❌ Acceso denegado: no hay sesión ni token.");
	    response.setStatus(HttpStatus.UNAUTHORIZED.value());
	    response.getWriter().write("Acceso denegado: no hay sesión ni token.");
    }


}
