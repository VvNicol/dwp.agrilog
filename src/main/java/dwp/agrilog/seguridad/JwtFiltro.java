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

		String token = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (token == null || !token.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}

		token = token.substring(7); // Remover "Bearer " del token

		try {
			Claims claims = JwtUtil.obtenerClaimsDesdeToken(token);
			String correo = claims.getSubject();
			String rol = claims.get("rol", String.class);

			if (correo != null && rol != null) {

				// Crear lista de autoridades (Spring Security requiere
				// Collection<GrantedAuthority>)
				List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(rol));

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(correo,
						null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("Token inválido o expirado");
			return;
		}

		chain.doFilter(request, response);
	}

}
