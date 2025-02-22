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

	    // Imprimir información de autenticación
	    if (SecurityContextHolder.getContext().getAuthentication() != null) {
	        System.out.println("✅ SecurityContextHolder: " + SecurityContextHolder.getContext().getAuthentication());
	    } else {
	        System.out.println("❌ SecurityContextHolder está vacío.");
	    }

	    // Revisar sesión normal
	    if (session == null) {
	        System.out.println("⚠️ No hay sesión activa.");
	        return new ModelAndView("redirect:/inicio/iniciar-sesion");
	    }

	    Object usuario = session.getAttribute("usuario");
	    Object rol = session.getAttribute("rol");

	    System.out.println("🔍 Sesión Activa:");
	    System.out.println("Usuario: " + usuario);
	    System.out.println("Rol: " + rol);

	    if (usuario == null) {
	        System.out.println("⚠️ Usuario no autenticado, redirigiendo...");
	        return new ModelAndView("redirect:/inicio/iniciar-sesion");
	    }

	    System.out.println("✅ Usuario autenticado correctamente.");
	    return new ModelAndView("usuario/usuarioPanel");
	}

}
