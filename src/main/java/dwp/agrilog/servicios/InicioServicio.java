package dwp.agrilog.servicios;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import dwp.agrilog.dto.UsuarioDTO;
import dwp.agrilog.utilidades.JwtUtil;
import dwp.agrilog.utilidades.Util;

/**
 * Servicio para gestionar el inicio de sesión y la verificación de correos
 * electrónicos.
 * 
 * Implementa la interfaz InicioInterfaz.
 * 
 * @autor nrojlla 25022025
 */
@Service
public class InicioServicio implements InicioInterfaz {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private PasswordEncoder contraseniaEncriptada;

	@Autowired
	private CorreoServicio correoServicio;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean verificarCorreo(String token) throws Exception {
		boolean verificado = false; // Control de verificación correcta

		try {

			// 1. Llamada a la API para validar el token
			String apiUrl = "http://localhost:7259/api/token-correo?token=" + token;

			ResponseEntity<Map> respuesta = restTemplate.getForEntity(apiUrl, Map.class);

			if (respuesta.getStatusCode() != HttpStatus.OK || respuesta.getBody() == null) {
				return false;
			}

			Map<String, Object> responseBody = respuesta.getBody();

			// 2. Verifica que la respuesta contenga los datos necesarios
			if (!responseBody.containsKey("correo") || !responseBody.containsKey("caducidad")) {
				return false;
			}

			String correo = responseBody.get("correo").toString();
			LocalDateTime caducidad = LocalDateTime.parse(responseBody.get("caducidad").toString());

			// 3. Verifica si el token ha caducado
			if (caducidad.isBefore(LocalDateTime.now())) {
				return false;
			}

			// 4. Actualiza el estado del usuario en la API
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setCorreo(correo);
			usuario.setCorreoValidado(true);

			HttpEntity<UsuarioDTO> solicitud = new HttpEntity<>(usuario);

			String validarCorreoUrl = "http://localhost:7259/api/validar-correo";// llamada a la api

			ResponseEntity<Map> validacionResponse = restTemplate.postForEntity(validarCorreoUrl, solicitud, Map.class);

			if (validacionResponse.getStatusCode() != HttpStatus.OK
					|| (validacionResponse.getBody() != null && validacionResponse.getBody().containsKey("error"))) {
				return false;
			}

			verificado = true;
			
		} catch (Exception ex) {
			
			return false;
		}

		return verificado;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, String> iniciarSesionUsuario(UsuarioDTO usuario) throws Exception {
	    try {
	        // 1. URL de la API para obtener la contraseña del usuario
	        String apiUrlIniciarSesion = "http://localhost:7259/api/contrasenia";

	        Map<String, String> request = new HashMap<>();
	        request.put("correo", usuario.getCorreo());

	        HttpEntity<Map<String, String>> solicitud = new HttpEntity<>(request);

	        // 2. Llamado a la API
	        ResponseEntity<Map> respuesta = restTemplate.postForEntity(apiUrlIniciarSesion, solicitud, Map.class);

	        if (respuesta.getStatusCode() != HttpStatus.OK || !respuesta.getBody().containsKey("contrasenia")) {
	            throw new Exception("Correo no encontrado o error interno.");
	        }

	        // 3. Obtener datos del usuario
	        Map<String, Object> usuarioData = respuesta.getBody();
	        String contraseniaGuardada = usuarioData.get("contrasenia").toString();
	        boolean correoValidado = (boolean) usuarioData.get("correoValidado");
	        String rol = usuarioData.get("rol").toString();

	        // 4. Verificar si el correo está validado
	        if (!correoValidado) {
	            String nuevoToken = Util.generarTokenConCorreo(usuario);
	            HttpEntity<UsuarioDTO> actualizarSolicitud = new HttpEntity<>(usuario);
	            String actualizarUrl = "http://localhost:8080/api/token-correo-actualizar";
	            restTemplate.postForEntity(actualizarUrl, actualizarSolicitud, Void.class);

	            correoServicio.correoDeVerificacion(usuario.getCorreo(), nuevoToken);
	            throw new Exception("Tu correo no está validado. Se ha enviado un nuevo correo de verificación.");
	        }

	        // 5. Verificar contraseña
	        if (!contraseniaEncriptada.matches(usuario.getContrasenia(), contraseniaGuardada)) {
	            throw new Exception("Contraseña incorrecta.");
	        }

	        // 6. Generar token JWT
	        String token = JwtUtil.generarToken(usuario.getCorreo(), rol);

	        // 7. Devolver respuesta
	        Map<String, String> respuestaMap = new HashMap<>();
	        respuestaMap.put("token", token);
	        respuestaMap.put("rol", rol);
	        return respuestaMap;

	    } catch (ResourceAccessException | HttpClientErrorException e) {
	        throw new Exception("Error interno. Por favor, intenta más tarde.");
	    } catch (Exception e) {
	        throw new Exception(e.getMessage(), e);
	    }
	}


}
