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

import dwp.agrilog.dto.TokenDto;
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
	        String contraseniaEncriptada = this.contraseniaEncriptada.encode(usuario.getContrasenia());
	        usuario.setRol("USUARIO");
	        usuario.setContrasenia(contraseniaEncriptada);
	        usuario.setFechaRegistro(java.time.LocalDateTime.now());

	        HttpEntity<UsuarioDTO> request = new HttpEntity<>(usuario);
	        ResponseEntity<UsuarioDTO> response = restTemplate.postForEntity(apiUrl, request, UsuarioDTO.class);

	        if (response.getStatusCode() != HttpStatus.CREATED || response.getBody() == null) {
	            throw new Exception("Error al registrar el usuario.");
	        }

	        UsuarioDTO usuarioRegistrado = response.getBody();
	        Long usuarioId = usuarioRegistrado.getUsuarioId();

	        UsuarioDTO usuarioToken = new UsuarioDTO();
	        usuarioToken.setUsuarioId(usuarioId);

	        TokenDto tokenDto = new TokenDto();
	        tokenDto.setUsuario(usuarioToken);

	        String token = Util.generarToken(tokenDto);

	        HttpEntity<TokenDto> tokenRequest = new HttpEntity<>(tokenDto);
	        String tokenUrl = "http://localhost:7259/api/token-correo-actualizar";
	        restTemplate.postForEntity(tokenUrl, tokenRequest, Void.class);

	        correoServicio.correoDeVerificacion(usuario.getCorreo(), token);

	        // ✅ AQUÍ es donde agregas tu mensaje manualmente
	        return "Usuario registrado con éxito. Verifique su correo para iniciar sesión.";

	    } catch (HttpClientErrorException e) {
	        e.printStackTrace();
	        throw new Exception(obtenerMensajeError(e.getResponseBodyAsString()));
	    } catch (Exception ex) {
	        ex.printStackTrace();
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
