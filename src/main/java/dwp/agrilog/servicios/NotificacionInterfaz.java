package dwp.agrilog.servicios;

import java.util.List;

import dwp.agrilog.dto.NotificacionDto;

/**
 * Interfaz para operaciones sobre notificaciones.
 * 
 * @author nrojlla
 * @date 27/05/2025
 */
public interface NotificacionInterfaz {

	/**
	 * 1. Obtiene la lista de notificaciones asociadas a un usuario.
	 * 
	 * @param usuarioId ID del usuario
	 * @return Lista de notificaciones
	 */
	public List<NotificacionDto> obtenerPorUsuario(Long usuarioId);

	/**
	 * 2. Marca una notificación como leída según su ID.
	 * 
	 * @param notificacionId ID de la notificación
	 */
	public void marcarComoLeida(Long notificacionId);

	/**
	 * 3. Verifica si el usuario tiene notificaciones nuevas sin leer.
	 * 
	 * @param usuarioId ID del usuario
	 * @return true si hay nuevas notificaciones, false si no
	 */
	public boolean hayNotificacionesNuevas(Long usuarioId);

}
