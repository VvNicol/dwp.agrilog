package dwp.agrilog.controladores;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dwp.agrilog.dto.UsuarioDTO;
import dwp.agrilog.servicios.RegistrarseServicio;

/**
 * Controlador para gestionar el registro de nuevos usuarios.
 * 
 * @autor nrojlla 25022025
 */
@Controller
@RequestMapping("/regis")
public class RegistrarseControlador {

	@Autowired
	private RegistrarseServicio registrarseServicio;

	/**
	 * Muestra la vista del formulario de registro.
	 *
	 * @return Vista de registro.
	 */
	@GetMapping("/registrarse")
	public ModelAndView mostrarRegistro() {
		return new ModelAndView("inicio/registrarse");
	}

	/**
	 * Registra un nuevo usuario en el sistema.
	 *
	 * @param usuario Objeto DTO con los datos del usuario.
	 * @return Respuesta con mensaje de éxito o error.
	 */
	@PostMapping("/registrarse")
	@ResponseBody
	public ResponseEntity<Map<String, String>> registrar(@RequestBody UsuarioDTO usuario) {
	    Map<String, String> response = new HashMap<>();
	    try {
	        registrarseServicio.registrarUsuario(usuario);
	        response.put("mensaje", "Usuario registrado con éxito. Verifique su correo para iniciar sesión.");
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        response.put("error", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	}


}
