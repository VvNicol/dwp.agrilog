package dwp.agrilog.utilidades;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Utilidad para manejar operaciones con tokens JWT.
 * Incluye generación, validación y extracción de datos.
 * 
 * @autor nrojlla
 * @fecha 27/05/2025
 */
public class JwtUtil {

	// 1. Clave secreta usada para firmar y verificar tokens
	private static final String claveSecreta = "tBHt8wR9Fr2hvsL9876543210123456proyectoAgricola";

	// 2. Duración del token (5 horas)
	private static final long caducidad = 1000 * 60 * 60 * 5;

	// 3. Clave generada a partir de la clave secreta
	private static final SecretKey clave = Keys.hmacShaKeyFor(claveSecreta.getBytes());

	// 4. Genera un token JWT con correo, rol, fecha de emisión y expiración
	public static String generarToken(String correo, String rol) {
	    return Jwts.builder()
	        .subject(correo)
	        .claims(Map.of("rol", rol))
	        .issuedAt(new Date())
	        .expiration(new Date(System.currentTimeMillis() + caducidad))
	        .signWith(clave, Jwts.SIG.HS256)
	        .compact();
	}

	// 5. Valida un token JWT; devuelve false si está mal firmado o expirado
	public static boolean validarToken(String token) {
		try {
			Jwts.parser().verifyWith(clave).build().parseSignedClaims(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

	// 6. Extrae el rol desde un token JWT
	public static String obtenerRolDesdeToken(String token) {
		Claims claims = Jwts.parser().verifyWith(clave).build().parseSignedClaims(token).getPayload();
		return claims.get("rol", String.class);
	}

	// 7. Extrae el correo (subject) desde un token JWT
	public static String obtenerCorreoDesdeToken(String token) {
		Claims claims = Jwts.parser().verifyWith(clave).build().parseSignedClaims(token).getPayload();
		return claims.getSubject();
	}

	// 8. Devuelve todos los claims (información) del token
	public static Claims obtenerClaimsDesdeToken(String token) {
		return Jwts.parser().verifyWith(clave).build().parseSignedClaims(token).getPayload();
	}

	// 9. Crea una lista de authorities para Spring Security con el rol dado
	public static List<SimpleGrantedAuthority> obtenerAuthorities(String rol) {
		return List.of(new SimpleGrantedAuthority(rol));
	}
}
