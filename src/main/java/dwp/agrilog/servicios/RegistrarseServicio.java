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
import jakarta.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	


	// Declararlo arriba en tu clase
	private static final Logger log = LoggerFactory.getLogger(RegistrarseServicio.class);


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
			log.info("Encripta la contraseña antes de enviarla a la API");
			
			// 2. Crea la solicitud HTTP con el usuario
			
			HttpEntity<UsuarioDTO> request = new HttpEntity<>(usuario);
			log.info("Crea la solicitud HTTP con el usuario: {}", request);

			
			// 3. Genera un token de verificación para el usuario
			String token = Util.generarTokenConCorreo(usuario);
			log.info("Genera un token de verificación para el usuario");
			// 4. Envía la solicitud a la API de registro
			ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
			log.info("Envía la solicitud a la API de registro");
			
			
			// 5. Si la respuesta no es 201 CREATED, extrae el mensaje de error
			if (response.getStatusCode() != HttpStatus.CREATED) {
				throw new Exception(obtenerMensajeError(response.getBody()));
			}

			log.info("2CORREO_CONTRASENIA: {}", System.getenv("CORREO_CONTRASENIA"));
			// 6. Envía el correo de verificación con el token
			try {
			    correoServicio.correoDeVerificacion(usuario.getCorreo(), token);
			    log.info("3CORREO_CONTRASENIA: {}", System.getenv("CORREO_CONTRASENIA"));
			} catch (MessagingException e) {
			    throw new Exception("No se pudo enviar el correo de verificación.", e);
			}
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
