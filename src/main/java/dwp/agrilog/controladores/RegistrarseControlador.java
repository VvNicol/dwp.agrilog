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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dwp.agrilog.dto.UsuarioDTO;
import dwp.agrilog.servicios.RegistrarseServicio;

@Controller
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/regis")
public class RegistrarseControlador {
	
	@Autowired
	private RegistrarseServicio registrarseServicio;
	
	@GetMapping("/registrarse")
	public ModelAndView mostrarRegistro() {
		return new ModelAndView("inicio/registrarse");
	}
	
	@PostMapping("/registrarse")
	@ResponseBody
	public ResponseEntity<Map<String, String>> registrar(UsuarioDTO usuario) {
		Map<String, String> response = new HashMap<>();
		try {
			registrarseServicio.registrarUsuario(usuario);
			response.put("mensaje", "Usuario registrado con éxito. Verifique su correo para iniciar sesión.");
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			response.put("error", "Error al registrar usuario: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

}
