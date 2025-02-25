package dwp.agrilog.servicios;

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
public class RegistrarseServicio {

	@Autowired
	private PasswordEncoder contraseniaEncriptada;

	@Autowired
	private CorreoServicio correoServicio;

	@Autowired
	private RestTemplate restTemplate;

	private final String apiUrl = "http://localhost:7259/api/registrarse";

	public String registrarUsuario(UsuarioDTO usuario) throws Exception {
		try {

			String contraseniaEncriptada = this.contraseniaEncriptada.encode(usuario.getContrasenia());
			usuario.setRol("USUARIO");
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

}
