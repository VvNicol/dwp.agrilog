package dwp.agrilog.controladores;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador para gestionar el cierre de sesión.
 * 
 * @autor nrojlla 05032025
 */
@Controller
@RequestMapping("/cerrar-sesion")
public class CerrarSesionControlador {

    /**
     * Cierra la sesión del usuario y lo redirige a la página de inicio de sesión.
     * 
     * @param solicitud  Objeto HttpServletRequest para obtener la sesión.
     * @param respuesta Objeto HttpServletResponse para redireccionar.
     * @return Redirección a la página de inicio de sesión.
     */
    @GetMapping
    public String cerrarSesion(HttpServletRequest solicitud, HttpServletResponse respuesta) {
        HttpSession sesion = solicitud.getSession(false); // Obtiene la sesión sin crear una nueva
        if (sesion != null) {
        	sesion.invalidate(); // Invalida la sesión
        }

        // Limpia el contexto de seguridad
        SecurityContextHolder.clearContext();

        // Redirige al usuario a la página de inicio de sesión
        return "redirect:/inicio/iniciar-sesion";
    }
}
