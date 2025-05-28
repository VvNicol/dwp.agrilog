package dwp.agrilog.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dwp.agrilog.dto.UsuarioDTO;

/**
 * Servicio para la gestión de usuarios por parte del administrador.
 * 
 * Implementa la interfaz AdminInterface.
 * 
 * @autor nrojlla 25022025
 */
@Service
public class AdminServicio implements AdminInterface{

	private final RestTemplate restTemplate = new RestTemplate();

	
	@SuppressWarnings("rawtypes")
	public List<UsuarioDTO> obtenerListaUsuarios() {
	    String url = "https://agrilog.nicoldev.es/api/lista-usuarios";

	    try {
	        ResponseEntity<Map[]> respuesta = restTemplate.getForEntity(url, Map[].class);
	        Map[] usuariosMap = respuesta.getBody();
	      
	        
	        List<UsuarioDTO> usuarios = new ArrayList<>();
	        for (Map usuario : usuariosMap) {
	            String correo = (String) usuario.get("correo");
	            String rol = (String) usuario.get("rol");

	            usuarios.add(new UsuarioDTO(correo, rol, true));

	        }

	        return usuarios;

	    } catch (Exception e) {
	        throw new RuntimeException("Error al obtener la lista de usuarios: " + e.getMessage());
	    }
	}
	
	public void eliminarUsuario(String correo) {
		String url = "https://agrilog.nicoldev.es/api/eliminar-usuario?correo=" + correo;
		try {
			restTemplate.delete(url);
		} catch (Exception e) {
			throw new RuntimeException("Error al eliminar usuario: " + e.getMessage());
		}
	}
}
