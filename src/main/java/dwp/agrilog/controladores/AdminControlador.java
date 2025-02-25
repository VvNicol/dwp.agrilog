package dwp.agrilog.controladores;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

/**
 * Controlador para admin autenticado.
 * 
 * @autor nrojlla 25022025
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
	
	/**
	 * Muestra el panel de administración si el usuario está autenticado.
	 * 
	 * @param session Sesión HTTP del usuario.
	 * @return Vista del panel de administrador o redirección al inicio de sesión.
	 */
	@GetMapping("/panel")
	public ModelAndView mostrarPanelUsuario(HttpSession session) {
		
	    var authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    // Verifica si el usuario está autenticado y si tiene roles asignados

	    if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().isEmpty()) {
	        return new ModelAndView("redirect:/inicio/iniciar-sesion");
	    }

	    return new ModelAndView("admin/adminPanel");
	}

}
