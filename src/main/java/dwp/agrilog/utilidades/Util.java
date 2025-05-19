package dwp.agrilog.utilidades;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import dwp.agrilog.dto.TokenDto;

/**
 * Clase de utilidades generales para generación de tokens y códigos.
 * 
 * @autor nrojlla 25022025
 */

public class Util {

	/**
	 * Genera un token único y lo asigna al usuario con una fecha de expiración.
	 *
	 * @param usuario Objeto DTO del usuario al que se le asignará el token.
	 * @return Token generado en formato UUID.
	 */
	public static String generarToken(TokenDto tokenUsuario) {
		String token = UUID.randomUUID().toString();
		tokenUsuario.setToken(token);
		tokenUsuario.setTokenExpiracionFecha(LocalDateTime.now().plusMinutes(5));
		return token;
	}

	/**
	 * Genera un código numérico aleatorio de 6 dígitos.
	 *
	 * @return Código de verificación de 6 dígitos.
	 */
	public static int generarCodigo() {
		return 100000 + new Random().nextInt(900000);
	}

}
