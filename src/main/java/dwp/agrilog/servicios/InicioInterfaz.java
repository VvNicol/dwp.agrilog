package dwp.agrilog.servicios;

import java.util.Map;

import dwp.agrilog.dto.UsuarioDTO;

/**
 * Interfaz para la gestión del inicio de sesión y verificación de correo.
 * 
 * @autor nrojlla 25022025
 */
public interface InicioInterfaz {

	/**
	 * Inicia sesión de un usuario autenticando sus credenciales.
	 *
	 * @param usuario DTO con los datos del usuario.
	 * @return Mapa con el token de autenticación y rol del usuario.
	 * @throws Exception Si ocurre un error en el inicio de sesión.
	 */
	public Map<String, String> iniciarSesionUsuario(UsuarioDTO usuario) throws Exception;

	/**
	 * Verificacion de correo electrónico a través de un token de validación.
	 *
	 * @param token Token de verificación recibido en el correo.
	 * @return `true` si la verificación es exitosa, `false` en caso contrario.
	 * @throws Exception Si ocurre un error en la verificación.
	 */
	public boolean verificarCorreo(String token) throws Exception;
}
