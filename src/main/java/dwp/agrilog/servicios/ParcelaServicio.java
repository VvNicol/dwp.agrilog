package dwp.agrilog.servicios;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dwp.agrilog.dto.ParcelaDto;

@Service
public class ParcelaServicio {

	
	private static final String PROTOCOLO = "https";
	private static final String DOMINIO = "agrilog.nicoldev.es";

	private final RestTemplate restTemplate = new RestTemplate();
	
	public void CrearNuevaParcela(ParcelaDto parcelaDto) throws Exception {
	    String url = PROTOCOLO + "://" + DOMINIO + "/api/parcela/crear";

	    HttpHeaders cabeceras = new HttpHeaders();
	    cabeceras.setContentType(MediaType.APPLICATION_JSON);

	    HttpEntity<ParcelaDto> peticion = new HttpEntity<>(parcelaDto, cabeceras);

	    System.out.println("Contenido del DTO enviado a la API:");
	    System.out.println("usuarioId: " + parcelaDto.getUsuarioId());
	    System.out.println("nombre: " + parcelaDto.getNombre());
	    System.out.println("descripcion: " + parcelaDto.getDescripcion());
	    System.out.println("fechaRegistro: " + parcelaDto.getFechaRegistro());

	    try {
	        @SuppressWarnings("rawtypes")
	        ResponseEntity<Map> respuesta = restTemplate.exchange(
	            url,
	            HttpMethod.POST,
	            peticion,
	            Map.class
	        );

	        if (respuesta.getStatusCode() != HttpStatus.CREATED) {
	            throw new RuntimeException("Error al crear la parcela en la API");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("No se pudo crear la parcela: " + e.getMessage());
	    }
	}


}
