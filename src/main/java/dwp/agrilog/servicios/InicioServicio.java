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
import org.springframework.web.client.RestTemplate;

import dwp.agrilog.dto.UsuarioDTO;
import dwp.agrilog.utilidades.Util;

@Service
public class InicioServicio implements InicioInterfaz {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private PasswordEncoder contraseniaEncriptada;

	@Autowired
	private CorreoServicio correoServicio;

	private final String apiUrl = "http://localhost:7259/api/registrarse";

	@Override
	public String registrarUsuario(UsuarioDTO usuario) throws Exception {
		try {

			String contraseniaEncriptada = this.contraseniaEncriptada.encode(usuario.getContrasenia());
			usuario.setRol("usuario");
			usuario.setContrasenia(contraseniaEncriptada);
			usuario.setFechaRegistro(java.time.LocalDateTime.now());
			HttpEntity<UsuarioDTO> request = new HttpEntity<>(usuario);

			String token = Util.generarTokenConCorreo(usuario);
			ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

			if (response.getStatusCode() != HttpStatus.CREATED) {
				throw new Exception(response.getBody());
			}

			correoServicio.correoDeVerificacion(usuario.getCorreo(), token);
			return response.getBody();

		} catch (Exception ex) {
			throw new Exception("Ha ocurrido algo inesperado: " + ex.getMessage(), ex);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean verificarCorreo(String token) throws Exception {
		try {
			String apiUrl = "http://localhost:7259/api/token-correo?token=" + token;

			// 1. Enviar solicitud GET a la API para verificar el token
			ResponseEntity<Map> respuesta = restTemplate.getForEntity(apiUrl, Map.class);

			// 2. Verificar si la API devolvió error
			if (respuesta.getStatusCode() != HttpStatus.OK || !respuesta.getBody().containsKey("correo")) {
				throw new Exception("Error al verificar el token: " + respuesta.getBody().get("error"));
			}

			// 3. Extraer el correo
			Map<String, Object> responseBody = respuesta.getBody();
			String correo = responseBody.get("correo").toString();
			LocalDateTime caducidad = LocalDateTime.parse(responseBody.get("caducidad").toString());

			System.out.println("✔️ Token válido. Correo: " + correo);

			// 4. Verificar si el token ha expirado
			if (caducidad.isBefore(LocalDateTime.now())) {
				throw new Exception("El token ha expirado.");
			}

			// 5. Crear el objeto UsuarioDTO con el correo y el campo validado
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setCorreo(correo);
			usuario.setCorreoValidado(true);

			HttpEntity<UsuarioDTO> request = new HttpEntity<>(usuario);

			// 6. Enviar usuario a la API para actualizarlo
			String validarCorreoUrl = "http://localhost:7259/api/validar-correo";
			ResponseEntity<Map> validacionResponse = restTemplate.postForEntity(validarCorreoUrl, request, Map.class);

			if (validacionResponse.getStatusCode() != HttpStatus.OK
					|| validacionResponse.getBody().containsKey("error")) {
				throw new Exception("Error al validar el correo: " + validacionResponse.getBody().get("error"));
			}

			return true;
		} catch (Exception ex) {

			throw new Exception("Ha ocurrido algo inesperado: " + ex.getMessage(), ex);
		}
	}

	@SuppressWarnings("rawtypes")
	public boolean iniciarSesionUsuario(UsuarioDTO usuario) throws Exception {

		try {
			String apiUrlIniciarSesion = "http://localhost:7259/api/contrasenia";
			// 1. Enviar correo a la API para obtener la contraseña
			Map<String, String> request = new HashMap<>();
			request.put("correo", usuario.getCorreo());

			HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(request);

			ResponseEntity<Map> respuesta = restTemplate.postForEntity(apiUrlIniciarSesion, requestEntity, Map.class);

			// 2. Verificar si la API devolvió un error
			if (respuesta.getStatusCode() != HttpStatus.OK || !respuesta.getBody().containsKey("contrasenia")) {
				throw new Exception("Correo no encontrado o error en la API.");
			}

			// 3. Obtener la contraseña encriptada desde la API
			String contraseniaGuardada = respuesta.getBody().get("contrasenia").toString();

			// 4. Comparar la contraseña encriptada con la que ingresó el usuario
			if (!contraseniaEncriptada.matches(usuario.getContrasenia(), contraseniaGuardada)) {
				return false; // Contraseña incorrecta
			}

			return true; // Usuario autenticado
			
		} catch (Exception e) {
			throw new Exception("Error en el inicio de sesión: " + e.getMessage(), e);
		}
	}

}
