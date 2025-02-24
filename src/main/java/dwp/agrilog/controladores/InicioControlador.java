package dwp.agrilog.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dwp.agrilog.dto.UsuarioDTO;
import dwp.agrilog.servicios.InicioServicio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/inicio")
public class InicioControlador {

	@Autowired
	private InicioServicio inicioServicio;
	
	@GetMapping("/principal")
    public ModelAndView mostrarIndex() {
        return new ModelAndView("inicio/index");
    }

	@GetMapping("/iniciar-sesion")
	public ModelAndView mostrarInicioSesion() {
		return new ModelAndView("inicio/iniciarSesion");
	}

	@GetMapping("/registrarse")
	public ModelAndView mostrarRegistro() {
		return new ModelAndView("inicio/registrarse");
	}

	@GetMapping("/cerrar-sesion")
	public ModelAndView cerrarSesion(HttpSession session) {
	    session.invalidate();
	    return new ModelAndView("redirect:inicio/iniciar-sesion");
	}

	@GetMapping("/verificar-correo")
	public ModelAndView verificarCorreo(@RequestParam("token") String token) {
	    ModelAndView modelAndView = new ModelAndView("inicio/verificarCorreo");

	    try {
	        boolean verificado = inicioServicio.verificarCorreo(token); // Llama al servicio

	        if (verificado) {
	            modelAndView.addObject("mensaje", "Correo verificado exitosamente. Ya puedes iniciar sesión.");
	        } else {
	            modelAndView.addObject("error", "El token es inválido o ha expirado.");
	        }
	    } catch (Exception e) {
	        // 🔥 Asegurar que el mensaje de error solo se muestra si NO se verificó correctamente
	        modelAndView.addObject("error", "Ha ocurrido un error inesperado. Intenta nuevamente.");
	    }

	    return modelAndView;
	}




	@PostMapping("/registrarse")
	@ResponseBody
	public ResponseEntity<Map<String, String>> registrar(UsuarioDTO usuario) {
		Map<String, String> response = new HashMap<>();
		try {
			inicioServicio.registrarUsuario(usuario);
			response.put("mensaje", "Usuario registrado con éxito. Verifique su correo para iniciar sesión.");
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			response.put("error", "Error al registrar usuario: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@PostMapping("/iniciar-sesion")
	public ModelAndView iniciarSesion(@RequestParam String correo, @RequestParam String contrasenia, 
	                                  HttpSession session, HttpServletRequest request, HttpServletResponse response) {
	    try {
	        UsuarioDTO usuario = new UsuarioDTO(correo, contrasenia);
	        Map<String, String> resultado = inicioServicio.iniciarSesionUsuario(usuario);

	        if (resultado.containsKey("token")) {
	            session.setAttribute("usuario", correo);
	            session.setAttribute("rol", resultado.get("rol"));
	            session.setAttribute("token", resultado.get("token"));

	            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(resultado.get("rol")));
	            UsernamePasswordAuthenticationToken authentication =
	                    new UsernamePasswordAuthenticationToken(correo, null, authorities);
	            
	            SecurityContext context = SecurityContextHolder.createEmptyContext();
	            context.setAuthentication(authentication);
	            SecurityContextHolder.setContext(context);

	            SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
	            securityContextRepository.saveContext(context, request, response);

	            return verificarYRedireccionar(resultado.get("rol"));
	        } else {
	            return new ModelAndView("inicio/iniciarSesion").addObject("error", "Correo o contraseña incorrectos.");
	        }
	    } catch (Exception e) {
	        return new ModelAndView("inicio/iniciarSesion").addObject("error", "Error al iniciar sesión: " + e.getMessage());
	    }
	}
	
	
	private ModelAndView verificarYRedireccionar(String rol) {
	    if ("ADMIN".equals(rol)) {
	        return new ModelAndView("redirect:/admin/panel");
	    } else if ("USUARIO".equals(rol)) {
	        return new ModelAndView("redirect:/usuario/panel");
	    } else {
	        return new ModelAndView("inicio/iniciarSesion").addObject("error", "Rol desconocido.");
	    }
	}


}
