package dwp.agrilog.controladores;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dwp.agrilog.dto.UsuarioDTO;
import dwp.agrilog.servicios.InicioServicio;

@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("/inicio")
public class InicioControlador {
	
	@Autowired
	private InicioServicio inicioServicio;
	
	
	@GetMapping("/registrarse")
    public ModelAndView mostrarFormularioRegistro() {
        return new ModelAndView("inicio/registrarse");
    }
	
	
	@GetMapping("/verificar-correo")
	@ResponseBody
	public ResponseEntity<Map<String, String>> verificarCorreo(@RequestParam("token") String token) {
		
	    System.out.println("🔍 Se recibió solicitud para verificar correo con token: " + token);

		
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
    @ResponseBody
    public ResponseEntity<Map<String, String>> iniciarSesion(@RequestBody UsuarioDTO usuario) {
        Map<String, String> response = new HashMap<>();
        try {
            boolean autenticado = inicioServicio.iniciarSesionUsuario(usuario);
            if (autenticado) {
                response.put("mensaje", "Has iniciado sesión con éxito.");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Correo o contraseña incorrectos.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.put("error", "Error al iniciar sesión: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


	
}
