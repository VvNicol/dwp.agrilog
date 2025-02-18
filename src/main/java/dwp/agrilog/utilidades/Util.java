package dwp.agrilog.utilidades;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import dwp.agrilog.dto.UsuarioDTO;

public class Util {
	
	public static String generarTokenConCorreo(UsuarioDTO usuario) {
	    String token = UUID.randomUUID().toString();
	    usuario.setToken(token);
	    usuario.setTokenExpiracionFecha(LocalDateTime.now().plusMinutes(5));
	    return token;
	}

	public static int generarCodigo() {
		return 100000 + new Random().nextInt(900000);
	}

}
