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
	    System.out.println("🔍 Verificando usuario en SecurityContextHolder...");
	    var authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().isEmpty()) {
	        System.out.println("⚠️ Usuario no autenticado o sin roles. Redirigiendo a inicio de sesión.");
	        return new ModelAndView("redirect:/inicio/iniciar-sesion");
	    }

	    System.out.println("✅ Usuario autenticado correctamente.");
	    System.out.println("✅ Rol en SecurityContextHolder: " + authentication.getAuthorities());
	    return new ModelAndView("usuario/usuarioPanel");
	}



}
