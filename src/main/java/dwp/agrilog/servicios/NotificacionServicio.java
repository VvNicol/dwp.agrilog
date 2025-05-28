package dwp.agrilog.servicios;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dwp.agrilog.dto.NotificacionDto;

@Service
public class NotificacionServicio implements NotificacionInterfaz {

	private final RestTemplate restTemplate = new RestTemplate();
	
	@Override
	public List<NotificacionDto> obtenerPorUsuario(Long usuarioId) {
	    String url = "https://agrilog.nicoldev.es/api/notificacion/usuario/" + usuarioId;
	    ResponseEntity<List<NotificacionDto>> respuesta = restTemplate.exchange(
	        url,
	        HttpMethod.GET,
	        null,
	        new ParameterizedTypeReference<List<NotificacionDto>>() {}
	    );
	    return respuesta.getBody();
	}

	@Override
	public void marcarComoLeida(Long notificacionId) {
	    String url = "https://agrilog.nicoldev.es/api/notificacion/marcar-leida/" + notificacionId;
	    restTemplate.postForEntity(url, null, Void.class);
	}

	@Override
	public boolean hayNotificacionesNuevas(Long usuarioId) {
	    List<NotificacionDto> notificaciones = obtenerPorUsuario(usuarioId);
	    return notificaciones.stream().anyMatch(n -> !n.isEstadoMensaje());
	}

}
