package dwp.agrilog.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dwp.agrilog.dto.UsuarioDTO;
import dwp.agrilog.servicios.InicioServicio;

@Controller
@RequestMapping("/inicio")
public class InicioControlador {
	
	@Autowired
	private InicioServicio inicioServicio;
	
	
	@GetMapping("/registrarse")
    public ModelAndView mostrarFormularioRegistro() {
        return new ModelAndView("inicio/registrarse");
    }
	
	@PostMapping("/registrarse")
    public ModelAndView registrar(UsuarioDTO usuario) {
        ModelAndView mv = new ModelAndView("inicio/resultadoRegistro");
        try {
            String respuestaApi = inicioServicio.registrarUsuario(usuario);
            mv.addObject("mensaje", "Usuario registrado con éxito. " + respuestaApi);
        } catch (Exception e) {
            mv.addObject("error", "Error al registrar usuario: " + e.getMessage());
        }
        return mv;
    }
	
}
