package dwp.agrilog.controladores;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import dwp.agrilog.servicios.RecuperarContraseniaServicio;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/form")
public class RecuperarContraseniaControlador {

	@Autowired
	private RecuperarContraseniaServicio recuperarcontraseniaServicio;

	@GetMapping("/recuperar-contrasenia")
	public ModelAndView mostrarRecuperarContrasenia() {
		return new ModelAndView("inicio/recuperarContrasenia");
	}

	@PostMapping("/codigo-enviar-correo")
	public ResponseEntity<Map<String, String>> enviarCodigo(@RequestBody Map<String, String> respuestaBody) {

		Map<String, String> responde = new HashMap<>();

		try {

			String correo = respuestaBody.get("correo");
			recuperarcontraseniaServicio.enviarCodigoAlCorreo(correo);

			responde.put("mensaje", "Se ha enviado un codigo al correo.");

			return new ResponseEntity<>(responde, HttpStatus.OK);

		} catch (Exception e) {
			responde.put("mensaje", "Error al intentar enviar el correo.");
			responde.put("error", e.getMessage());
			return new ResponseEntity<>(responde, HttpStatus.BAD_REQUEST);
		}

	}
	
	@PostMapping("/codigo-verificar")
	public ResponseEntity<Map<String, String>> verificarCodigo(@RequestBody Map<String, String> respuestaBody) {
		Map<String, String> responde = new HashMap<>();

		try {

			String correo = respuestaBody.get("correo");
			int codigo = Integer.parseInt(respuestaBody.get("codigo"));
			recuperarcontraseniaServicio.verificarCodigo(correo, codigo);

			responde.put("mensaje", "Se ha verificado con exito el codigo.");

			return new ResponseEntity<>(responde, HttpStatus.OK);

		} catch (Exception e) {
			responde.put("mensaje", "Error al intentar verificar el codigo.");
			responde.put("error", e.getMessage());
			return new ResponseEntity<>(responde, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/nueva-contrasenia")
	public ResponseEntity<Map<String, String>> cambiarConstrasenia(@RequestBody Map<String, String> respuestaBody) {

		Map<String, String> responde = new HashMap<>();

		try {
			String correo = respuestaBody.get("correo");

			String nuevaContrasenia = respuestaBody.get("nuevaContrasenia");

			recuperarcontraseniaServicio.cambiarContrasenia(correo, nuevaContrasenia);

			responde.put("mensaje", "Contraseña cambiada con éxito.");
			return new ResponseEntity<>(responde, HttpStatus.OK);

		} catch (Exception e) {

			responde.put("mensaje", "Error al cambiar la contraseña.");
			responde.put("error", e.getMessage());
			return new ResponseEntity<>(responde, HttpStatus.BAD_REQUEST);
		}

	}

	
}
