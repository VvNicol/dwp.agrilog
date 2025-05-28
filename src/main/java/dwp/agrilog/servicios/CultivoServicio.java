package dwp.agrilog.servicios;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import dwp.agrilog.dto.CultivoDto;

/**
 * Servicio para gestionar el CRUD de cultivos.
 * Implementa la interfaz CultivoInterfaz.
 * 
 * @author nrojlla
 * @date 25/02/2025
 */
@Service
public class CultivoServicio implements CultivoInterfaz {

	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public void crearNuevoCultivo(CultivoDto cultivoDto) throws Exception {
		String url = "https://agrilog.nicoldev.es/api/cultivo/crear";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CultivoDto> request = new HttpEntity<>(cultivoDto, headers);

		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, request,
				new ParameterizedTypeReference<Map<String, Object>>() {
				});

		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new RuntimeException("Error en la API: " + response.getBody().get("error"));
		}

	}

	@Override
	public List<CultivoDto> obtenerCultivosPorUsuario(Long usuarioId) {
		String url = "https://agrilog.nicoldev.es/api/cultivo/usuario/" + usuarioId;

		try {
			ResponseEntity<List<CultivoDto>> respuesta = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<CultivoDto>>() {
					});

			List<CultivoDto> lista = respuesta.getBody();

			// Ordenar por fechaRegistro descendente
			lista.sort((a, b) -> b.getFechaRegistro().compareTo(a.getFechaRegistro()));

			return lista;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error al obtener cultivos del usuario: " + e.getMessage());
		}
	}

	@Override
	public void eliminarCultivo(Long id) throws Exception {
		String url = "https://agrilog.nicoldev.es/api/cultivo/eliminar/" + id;

		try {
			restTemplate.delete(url);
		} catch (HttpClientErrorException e) {
			System.err.println("Error desde la API:");
			System.err.println("Código: " + e.getStatusCode());
			System.err.println("Cuerpo: " + e.getResponseBodyAsString());

			// Mensaje genérico para el usuario
			throw new Exception("No se pudo eliminar el cultivo. Intente nuevamente.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error inesperado al intentar eliminar el cultivo.");
		}
	}

	@Override
	public CultivoDto buscarPorId(Long id) {
		String url = "https://agrilog.nicoldev.es/api/cultivo/" + id;

		try {
			ResponseEntity<CultivoDto> response = restTemplate.getForEntity(url, CultivoDto.class);
			return response.getBody();

		} catch (HttpClientErrorException.NotFound e) {
			System.err.println("Cultivo no encontrado con ID: " + id);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void actualizarCultivo(CultivoDto cultivo) throws Exception {
		String url = "https://agrilog.nicoldev.es/api/cultivo/actualizar/" + cultivo.getCultivoId();

		try {
			HttpEntity<CultivoDto> request = new HttpEntity<>(cultivo);
			restTemplate.put(url, request); // PUT no devuelve cuerpo
		} catch (HttpClientErrorException e) {
			throw new Exception("No se pudo actualizar el cultivo.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error inesperado al actualizar el cultivo.");
		}
	}

}
