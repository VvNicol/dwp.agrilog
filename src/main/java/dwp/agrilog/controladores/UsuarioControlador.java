package dwp.agrilog.controladores;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsuarioControlador {

    @GetMapping("/usuario/panel")
    public ModelAndView mostrarPanelUsuario(HttpSession session) {
        Object usuario = session.getAttribute("usuario");
        Object rol = session.getAttribute("rol");

        if (usuario == null) {
            return new ModelAndView("redirect:/html/inicio/iniciarSesion.jsp");
        }

        ModelAndView mav = new ModelAndView("usuarioPanel");
        mav.addObject("usuario", usuario);
        mav.addObject("rol", rol);
        return mav;
    }
}
