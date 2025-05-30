package dwp.agrilog.servicios;

import java.util.List;

import dwp.agrilog.dto.UsuarioDTO;

/**
 * Interfaz para la gestión de administradores.
 * 
 * @autor nrojlla 25022025
 */
public interface AdminInterface {

	/**
	 * Obtiene la lista de todos los usuarios desde la API.
	 * 
	 * @return Lista de usuarios.
	 */
	public List<UsuarioDTO> obtenerListaUsuarios();
	
	/**
	 * Envía una solicitud a la API para eliminar un usuario por su correo.
	 * 
	 * @param correo Correo del usuario a eliminar.
	 */
	public void eliminarUsuario(String correo);
	
}
