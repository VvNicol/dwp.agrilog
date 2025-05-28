package dwp.agrilog.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dwp.agrilog.dto.NotificacionDto;
import dwp.agrilog.servicios.NotificacionServicio;

/**
 * Controlador encargado de manejar las operaciones relacionadas con las notificaciones de los usuarios.
 * 
 * @author nrojlla
 * @date 28052025
 */
@Controller
@RequestMapping("/notificacion")
public class NotificacionControlador {

	@Autowired
	private NotificacionServicio notificacionServicio;

	/**
	 * Muestra la vista con las notificaciones de un usuario.
	 * 
	 * @param id ID del usuario
	 * @param model Modelo de datos para la vista
	 * @return Nombre de la vista JSP donde se muestran las notificaciones
	 */
	@GetMapping("/usuario/{id}")
	public String obtenerNotificaciones(@PathVariable Long id, Model model) {
	    List<NotificacionDto> notificaciones = notificacionServicio.obtenerPorUsuario(id);
	    model.addAttribute("notificaciones", notificaciones);
	    return "usuario/usuarioNotificacion"; // Asegúrate de que este JSP existe en /jsp/usuario/
	}

	/**
	 * Verifica si el usuario tiene notificaciones nuevas sin leer.
	 * 
	 * @param id ID del usuario
	 * @return {@code true} si tiene nuevas, {@code false} en caso contrario o error
	 */
	@GetMapping("/usuario/{id}/nuevas")
	@ResponseBody
	public ResponseEntity<Boolean> tieneNuevas(@PathVariable Long id) {
		try {
			boolean hayNuevas = notificacionServicio.hayNotificacionesNuevas(id);
			return ResponseEntity.ok(hayNuevas);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}

	/**
	 * Marca una notificación específica como leída.
	 * 
	 * @param id ID de la notificación
	 * @return Mensaje de éxito o error en el cuerpo de la respuesta
	 */
	@PostMapping("/marcar-leida/{id}")
	@ResponseBody
	public ResponseEntity<String> marcarComoLeida(@PathVariable Long id) {
		try {
			notificacionServicio.marcarComoLeida(id);
			return ResponseEntity.ok("Notificación marcada como leída.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al marcar la notificación.");
		}
	}
}
