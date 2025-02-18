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

	@SuppressWarnings("rawtypes")
	public boolean verificarCorreo(String token) throws Exception {
	    try {
	        String apiUrl = "http://localhost:7259/api/token-correo?token=" + token;

	    
			ResponseEntity<Map> respuesta = restTemplate.getForEntity(apiUrl, Map.class);

	        if (respuesta.getStatusCode() != HttpStatus.OK || !respuesta.getBody().containsKey("token")) {
	            throw new Exception("Error al verificar el token: " + respuesta.getBody().get("error"));
	        }

	        @SuppressWarnings("unchecked")
			Map<String, Object> responseBody = respuesta.getBody();
	        String tokenRecibido = responseBody.get("token").toString();
	        LocalDateTime caducidad = LocalDateTime.parse(responseBody.get("caducidad").toString());

	        if (caducidad.isBefore(LocalDateTime.now())) {
	            throw new Exception("El token ha expirado.");
	        }

	        String validarCorreoUrl = "http://localhost:7259/api/validar-correo";
	        Map<String, String> request = new HashMap<>();
	        request.put("token", tokenRecibido);

	        ResponseEntity<String> validacionResponse = restTemplate.postForEntity(validarCorreoUrl, request, String.class);
	        if (validacionResponse.getStatusCode() != HttpStatus.OK) {
	            throw new Exception("Error al validar el correo.");
	        }

	        return true;
	    } catch (Exception ex) {
	        throw new Exception("Ha ocurrido algo inesperado: " + ex.getMessage(), ex);
	    }
	}


}
