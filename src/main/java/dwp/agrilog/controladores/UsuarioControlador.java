package dwp.agrilog.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

	@GetMapping("/usuario/panel")
    public ModelAndView mostrarPanelUsuario(HttpSession session) {
        if (session == null || session.getAttribute("usuario") == null) {
            return new ModelAndView("redirect:/inicio/iniciar-sesion");
        }
        return new ModelAndView("usuario/usuarioPanel"); // Carga usuarioPanel.jsp
    }
}
