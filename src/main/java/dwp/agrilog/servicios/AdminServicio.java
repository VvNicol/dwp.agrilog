package dwp.agrilog.servicios;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Servicio para la gestión de usuarios por parte del administrador.
 * 
 * @autor nrojlla 25022025
 */
@Service
public class AdminServicio {

	private final String API_URL = "http://localhost:7259/api";
	private final RestTemplate restTemplate = new RestTemplate();

	/**
	 * Obtiene la lista de todos los usuarios desde la API.
	 * 
	 * @return Lista de usuarios.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> obtenerListaUsuarios() {
		String url = API_URL + "/lista-usuarios";
		try {
			ResponseEntity<Map[]> respuesta = restTemplate.getForEntity(url, Map[].class);
			return Arrays.asList(respuesta.getBody());
		} catch (Exception e) {
			throw new RuntimeException("Error al obtener la lista de usuarios: " + e.getMessage());
		}
	}

	/**
	 * Envía una solicitud a la API para eliminar un usuario por su correo.
	 * 
	 * @param correo Correo del usuario a eliminar.
	 */
	public void eliminarUsuario(String correo) {
		String url = API_URL + "/eliminar-usuario?correo=" + correo;
		try {
			restTemplate.delete(url);
		} catch (Exception e) {
			throw new RuntimeException("Error al eliminar usuario: " + e.getMessage());
		}
	}
}
