package dwp.agrilog.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dwp.agrilog.servicios.AdminServicio;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador para admin autenticado.
 * 
 * @autor nrojlla 25022025
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {

	@Autowired
	private AdminServicio adminServicio;

	/**
	 * Muestra el panel de administración si el usuario está autenticado.
	 * 
	 * @param session Sesión HTTP del usuario.
	 * @return Vista del panel de administrador con la lista de usuarios o
	 *         redirección al inicio de sesión.
	 */
	@GetMapping("/panel")
	public ModelAndView mostrarPanelAdmin(HttpSession session) {
		var authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verifica si el usuario está autenticado y si tiene roles asignados
		if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().isEmpty()) {
			return new ModelAndView("redirect:/inicio/iniciar-sesion");
		}

		// Obtener la lista de usuarios desde la API
		List<Map<String, Object>> usuarios = adminServicio.obtenerListaUsuarios();

		ModelAndView modelAndView = new ModelAndView("admin/adminPanel");
		modelAndView.addObject("usuarios", usuarios);

		return modelAndView;
	}

	/**
	 * Maneja la eliminación de usuarios desde el panel de administración.
	 * 
	 * @param correo Correo del usuario a eliminar.
	 * @return Redirección al panel con la lista de usuarios actualizada.
	 */
	@PostMapping("/eliminar-usuario")
	public ModelAndView eliminarUsuario(@RequestParam String correo) {
		adminServicio.eliminarUsuario(correo);
		return new ModelAndView("redirect:/admin/panel");
	}

}
