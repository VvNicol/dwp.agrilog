package dwp.agrilog.servicios;

import java.util.List;
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
import io.jsonwebtoken.lang.Arrays;

/**
 * Servicio para manejar operaciones relacionadas con parcelas. Implementa la
 * interfaz ParcelaInterfaz.
 * 
 * @autor nrojlla
 * @fecha 27/05/2025
 */
@Service
public class ParcelaServicio implements ParcelaInterfaz {

	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public List<ParcelaDto> obtenerParcelasPorUsuario(Long usuarioId) {
		String url = "https://agrilog.nicoldev.es/api/parcela/usuario/" + usuarioId;

		try {
			ResponseEntity<ParcelaDto[]> respuesta = restTemplate.getForEntity(url, ParcelaDto[].class);
			return Arrays.asList(respuesta.getBody());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error al obtener parcelas del usuario: " + e.getMessage());
		}
	}

	@Override
	public void CrearNuevaParcela(ParcelaDto parcelaDto) throws Exception {
		String url = "https://agrilog.nicoldev.es/api/parcela/crear";

		HttpHeaders cabeceras = new HttpHeaders();
		cabeceras.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ParcelaDto> peticion = new HttpEntity<>(parcelaDto, cabeceras);

		try {
			@SuppressWarnings("rawtypes")
			ResponseEntity<Map> respuesta = restTemplate.exchange(url, HttpMethod.POST, peticion, Map.class);

			if (respuesta.getStatusCode() != HttpStatus.CREATED) {
				throw new RuntimeException("Error al crear la parcela en la API");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("No se pudo crear la parcela: " + e.getMessage());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Long CrearNuevaParcelaYObtenerId(ParcelaDto parcelaDto) throws Exception {
		String url = "https://agrilog.nicoldev.es/api/parcela/crear";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ParcelaDto> request = new HttpEntity<>(parcelaDto, headers);

		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

		if (response.getStatusCode() == HttpStatus.CREATED) {
			Map<String, Object> body = response.getBody();
			if (body != null && body.get("parcelaId") != null) {
				return Long.parseLong(body.get("parcelaId").toString());
			}
		}

		throw new RuntimeException("La API no devolvió el ID de la parcela.");
	}

}
