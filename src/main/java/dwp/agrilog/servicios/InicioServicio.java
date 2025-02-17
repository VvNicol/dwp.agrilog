package dwp.agrilog.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dwp.agrilog.dto.UsuarioDTO;

@Service
public class InicioServicio implements InicioInterfaz {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PasswordEncoder contraseniaEncriptada;
	
	private final String apiUrl = "http://localhost:7259/api/registrarse";
	
	@Override
	public String registrarUsuario(UsuarioDTO usuario) throws Exception {
	    try {
	        String contraseniaEncriptada = this.contraseniaEncriptada.encode(usuario.getContrasenia());
	        usuario.setContrasenia(contraseniaEncriptada);
	        
	        HttpEntity<UsuarioDTO> request = new HttpEntity<>(usuario);
	        
	        // Realiza la llamada POST a la API
	        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
	        
	        if (response.getStatusCode() != HttpStatus.CREATED) {
	            throw new Exception("Error al registrar usuario en la API: " + response.getBody());
	        }
	        
	        return response.getBody();  
	    } catch(Exception ex) {
	        throw new Exception("Error en la llamada a la API: " + ex.getMessage(), ex);
	    }
	}


}
