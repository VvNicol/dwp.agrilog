package dwp.agrilog.controladores;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

	
	@GetMapping("/panel")
	public ModelAndView mostrarPanelUsuario(HttpSession session) {
		
	    var authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().isEmpty()) {
	        return new ModelAndView("redirect:/inicio/iniciar-sesion");
	    }

	    return new ModelAndView("usuario/usuarioPanel");
	}



}
