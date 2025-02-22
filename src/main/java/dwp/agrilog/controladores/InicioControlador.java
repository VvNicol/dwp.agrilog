package dwp.agrilog.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/inicio")
public class InicioControlador {

	@Autowired
	private InicioServicio inicioServicio;
	
	@GetMapping("/")
    public ModelAndView mostrarIndex() {
        return new ModelAndView("index");
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
	@ResponseBody
	public ResponseEntity<Map<String, String>> verificarCorreo(@RequestParam("token") String token) {

		Map<String, String> response = new HashMap<>();
		try {
			boolean verificado = inicioServicio.verificarCorreo(token);
			if (verificado) {
				response.put("mensaje", "Correo verificado exitosamente. Ya puedes iniciar sesión.");
				return ResponseEntity.ok(response);
			} else {
				response.put("error", "El token es inválido o ha expirado.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			response.put("error", "Error al verificar correo: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
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
	public ModelAndView iniciarSesion(@RequestParam String correo, @RequestParam String contrasenia, HttpSession session) {
	    try {
	    	UsuarioDTO usuario = new UsuarioDTO(correo, contrasenia);
	        Map<String, String> resultado = inicioServicio.iniciarSesionUsuario(usuario);

	        if (resultado.containsKey("token")) {
	            session.setAttribute("usuario", correo);
	            session.setAttribute("rol", resultado.get("rol")); // 👈 Guardar solo "USUARIO" sin "ROLE_"
	            session.setAttribute("token", resultado.get("token"));

	            System.out.println("✅ Sesión iniciada para: " + session.getAttribute("usuario"));
	            System.out.println("✅ Rol en sesión: " + session.getAttribute("rol"));

	            // 🔥 Agregar usuario al SecurityContextHolder
	            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(resultado.get("rol")));
	            UsernamePasswordAuthenticationToken authentication =
	                    new UsernamePasswordAuthenticationToken(correo, null, authorities);
	            SecurityContextHolder.getContext().setAuthentication(authentication);

	            System.out.println("✅ Usuario autenticado en SecurityContextHolder: " + authentication);

	            if ("ADMIN".equals(resultado.get("rol"))) { // 👈 Comparar sin "ROLE_"
	                return new ModelAndView("redirect:/admin/panel");
	            } else {
	                return new ModelAndView("redirect:/usuario/panel");
	            }
	        } else {
	            ModelAndView mav = new ModelAndView("inicio/iniciarSesion");
	            mav.addObject("error", "Correo o contraseña incorrectos.");
	            return mav;
	        }
	    } catch (Exception e) {
	        ModelAndView mav = new ModelAndView("inicio/iniciarSesion");
	        mav.addObject("error", "Error al iniciar sesión: " + e.getMessage());
	        return mav;
	    }
	}



}
