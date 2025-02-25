package dwp.agrilog.servicios;

import dwp.agrilog.dto.UsuarioDTO;

/**
 * Interfaz para gestionar el registro de nuevos usuarios.
 * 
 * @autor nrojlla 25022025
 */
public interface RegistrarseInterfaz {

	/**
	 * Registra un nuevo usuario en el sistema.
	 * 
	 * @param usuario DTO con los datos del usuario a registrar.
	 * @return Mensaje de confirmación del registro.
	 * @throws Exception Si ocurre un error durante el registro.
	 */
	public String registrarUsuario(UsuarioDTO usuario) throws Exception;

}
