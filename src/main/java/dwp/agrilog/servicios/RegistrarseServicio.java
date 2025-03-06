package dwp.agrilog.servicios;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import dwp.agrilog.dto.UsuarioDTO;
import dwp.agrilog.utilidades.Util;

/**
 * Servicio para gestionar el registro de nuevos usuarios. Implementa la
 * interfaz RegistrarseInterfaz.
 * 
 * @autor nrojlla 25022025
 */
@Service
public class RegistrarseServicio implements RegistrarseInterfaz {

	@Autowired
	private PasswordEncoder contraseniaEncriptada;

	@Autowired
	private CorreoServicio correoServicio;

	@Autowired
	private RestTemplate restTemplate;

	// URL de la API para registrar un nuevo usuario
	private final String apiUrl = "http://localhost:7259/api/registrarse";

	@Override
	public String registrarUsuario(UsuarioDTO usuario) throws Exception {
		try {
			// 1. Encripta la contraseña antes de enviarla a la API
			String contraseniaEncriptada = this.contraseniaEncriptada.encode(usuario.getContrasenia());
			usuario.setRol("USUARIO");
			usuario.setContrasenia(contraseniaEncriptada);
			usuario.setFechaRegistro(java.time.LocalDateTime.now());

			// 2. Crea la solicitud HTTP con el usuario
			HttpEntity<UsuarioDTO> request = new HttpEntity<>(usuario);

			// 3. Genera un token de verificación para el usuario
			String token = Util.generarTokenConCorreo(usuario);

			// 4. Envía la solicitud a la API de registro
			ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

			// 5. Si la respuesta no es 201 CREATED, extrae el mensaje de error
			if (response.getStatusCode() != HttpStatus.CREATED) {
				throw new Exception(obtenerMensajeError(response.getBody()));
			}

			// 6. Envía el correo de verificación con el token
			correoServicio.correoDeVerificacion(usuario.getCorreo(), token);

			// 7. Retorna la respuesta de la API
			return response.getBody();

		} catch (HttpClientErrorException e) {
			throw new Exception(obtenerMensajeError(e.getResponseBodyAsString()));
		} catch (Exception ex) {
			throw new Exception("Error inesperado al registrar usuario.", ex);
		}
	}

	/**
	 * Extrae el mensaje de error de la respuesta de la API.
	 */
	private String obtenerMensajeError(String responseBody) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, String> errorMap = objectMapper.readValue(responseBody,
					new TypeReference<Map<String, String>>() {
					});
			return errorMap.getOrDefault("error", "Ha ocurrido un error desconocido.");
		} catch (Exception e) {
			return "Error desconocido al procesar la respuesta.";
		}
	}

}
