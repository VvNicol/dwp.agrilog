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

public class JwtUtil {

	private static final String claveSecreta = "tBHt8wR9Fr2hvsL9876543210123456proyectoAgricola";
	private static final long caducidad = 1000 * 60 * 60 * 5; // 5 horas
	private static final SecretKey clave = Keys.hmacShaKeyFor(claveSecreta.getBytes());

	public static String generarToken(String correo, String rol) {
	    return Jwts.builder()
	        .subject(correo)
	        .claims(Map.of("rol", rol)) // 👈 Ensure "ROLE_" is prefixed correctly
	        .issuedAt(new Date())
	        .expiration(new Date(System.currentTimeMillis() + caducidad))
	        .signWith(clave, Jwts.SIG.HS256) // 👈 Ensure signing with HS256
	        .compact();
	}


	public static boolean validarToken(String token) {
		try {
			Jwts.parser().verifyWith(clave).build().parseSignedClaims(token);

			return true;

		} catch (JwtException e) {
			return false;
		}
	}

	public static String obtenerRolDesdeToken(String token) {

		Claims claims = Jwts.parser().verifyWith(clave).build().parseSignedClaims(token).getPayload();

		return claims.get("rol", String.class);

	}

	public static String obtenerCorreoDesdeToken(String token) {

		Claims claims = Jwts.parser().verifyWith(clave).build().parseSignedClaims(token).getPayload();

		return claims.getSubject();

	}

	public static Claims obtenerClaimsDesdeToken(String token) {
		return Jwts.parser().verifyWith(clave).build().parseSignedClaims(token).getPayload();
	}

	public static List<SimpleGrantedAuthority> obtenerAuthorities(String rol) {
		return List.of(new SimpleGrantedAuthority(rol));
	}

}
