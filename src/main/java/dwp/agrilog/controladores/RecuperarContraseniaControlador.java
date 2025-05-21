package dwp.agrilog.controladores;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import dwp.agrilog.servicios.RecuperarContraseniaServicio;

/**
 * Controlador para gestionar la recuperacion de contrasenia.
 * 
 * @autor nrojlla 25022025
 */
@RestController
@RequestMapping("/form")
public class RecuperarContraseniaControlador {

	@Autowired
	private RecuperarContraseniaServicio recuperarcontraseniaServicio;

	/**
	 * Muestra la vista de recuperación de contraseña.
	 * 
	 * @return La vista "recuperarContrasenia.jsp".
	 */
	@GetMapping("/recuperar-contrasenia")
	public ModelAndView mostrarRecuperarContrasenia() {
		return new ModelAndView("inicio/recuperarContrasenia");
	}

	/**
	 * Envía un código de recuperación al correo del usuario.
	 *
	 * @param respuestaBody Contiene el correo del usuario.
	 * @return Mensaje de éxito o error.
	 */
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

	/**
	 * Verifica el código de recuperación ingresado por el usuario.
	 *
	 * @param respuestaBody Contiene el correo y el código ingresado.
	 * @return Mensaje de éxito o error.
	 */
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

	/**
	 * Cambia la contraseña del usuario después de la verificación.
	 *
	 * @param respuestaBody Contiene el correo y la nueva contraseña.
	 * @return Mensaje de éxito o error.
	 */
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
