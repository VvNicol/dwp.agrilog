package dwp.agrilog.servicios;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Servicio para gestionar la recuperacion de contraseña Implementa la interfaz
 * RecuperarContraseniaInterfaz.
 * 
 * @author nrojlla04032025
 */
@Service
public class RecuperarContraseniaServicio implements RecuperarContraseniaInterfaz {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CorreoServicio correoServicio;

	@Autowired
	private PasswordEncoder contraseniaEncriptada;

	@Override
	@SuppressWarnings("rawtypes")
	public void enviarCodigoAlCorreo(String correo) throws IOException {
	    String apiBuscarCorreo = "http://localhost:7259/api/contrasenia";
	    Map<String, String> solicitud = new HashMap<>();
	    solicitud.put("correo", correo);

	    ResponseEntity<Map> respuesta;

	    try {
	        respuesta = restTemplate.postForEntity(apiBuscarCorreo, solicitud, Map.class);
	        
	        if (respuesta.getStatusCode() != HttpStatus.OK || respuesta.getBody() == null) {
	            throw new RuntimeException("El correo no está registrado en nuestro sistema.");
	        }

	    } catch (Exception e) {
	        // Si la API no responde, muestra un error más amigable
	        throw new RuntimeException("Error interno del servidor. Inténtelo más tarde.");
	    }

	    // 2. Generar código de recuperación y definir la fecha de expiración
	    int codigo = dwp.agrilog.utilidades.Util.generarCodigo();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	    String expiracion = LocalDateTime.now().plusMinutes(10).format(formatter);

	    // 3. Guardar el código en la base de datos a través de la API
	    String apiGuardarCodigo = "http://localhost:7259/api/guardar-codigo";
	    Map<String, String> guardarCodigo = new HashMap<>();
	    guardarCodigo.put("correo", correo);
	    guardarCodigo.put("codigo", String.valueOf(codigo));
	    guardarCodigo.put("expiracion", expiracion);

	    try {
	        restTemplate.postForEntity(apiGuardarCodigo, guardarCodigo, Void.class);
	    } catch (Exception e) {
	        throw new RuntimeException("Error al guardar el código. Inténtelo más tarde.");
	    }

	    // 4. Enviar el código al correo del usuario
	    correoServicio.correoRecuperacionConCodigo(correo, codigo);
	}


	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void verificarCodigo(String correo, int codigo) throws IOException {

		// 1. Obtener el código almacenado desde la API
		String apiObtenerCodigo = "http://localhost:7259/api/obtener-codigo";

		// 2 Enviar la solicitud a la API para obtener el código almacenado
		Map<String, String> solicitud = new HashMap<>();
		solicitud.put("correo", correo);

		ResponseEntity<Map> respuesta;

		try {
			respuesta = restTemplate.postForEntity(apiObtenerCodigo, solicitud, Map.class);
		} catch (Exception e) {
			throw new RuntimeException("Error interno del servidor. Inténtelo más tarde.");
		}

		if (respuesta.getStatusCode() != HttpStatus.OK || respuesta.getBody() == null) {
			throw new RuntimeException("No se encontró un código asociado a este correo.");
		}

		// 3 Extraer los datos de la respuesta
		Map<String, Object> datos = respuesta.getBody();

		if (!datos.containsKey("codigo") || !datos.containsKey("expiracion")) {
			throw new RuntimeException("La API no devolvió los datos esperados.");
		}

		// 4 Conversión del código de String a int
		int codigoAlmacenado;
		try {
			codigoAlmacenado = Integer.parseInt(datos.get("codigo").toString());
		} catch (NumberFormatException e) {
			throw new RuntimeException("Error al convertir el código a número: " + e.getMessage());
		}

		// 5 Conversión la fecha de expiración
		LocalDateTime expiracion;
		try {
			expiracion = LocalDateTime.parse(datos.get("expiracion").toString());
		} catch (Exception e) {
			throw new RuntimeException("Error al convertir la fecha de expiración: " + e.getMessage());
		}

		// Comparación del código
		if (codigo != codigoAlmacenado) {
			throw new RuntimeException("El código ingresado es incorrecto.");
		}

		// Verificar si el código ha expirado
		if (expiracion.isBefore(LocalDateTime.now())) {
			throw new RuntimeException("El código ha caducado.");
		}
	}

	@Override
	public void cambiarContrasenia(String correo, String nuevaContrasenia) throws IOException {
	    String apiCambiarContrasenia = "https://localhost:7259/api/cambiar-contrasenia";

	    // Encriptar la nueva contraseña
	    String contraseniaEncriptada = this.contraseniaEncriptada.encode(nuevaContrasenia);

	    Map<String, String> solicitud = new HashMap<>();
	    solicitud.put("correo", correo);
	    solicitud.put("nuevaContrasenia", contraseniaEncriptada);

	    try {
	        ResponseEntity<Void> respuesta = restTemplate.postForEntity(apiCambiarContrasenia, solicitud, Void.class);

	        if (respuesta.getStatusCode() != HttpStatus.OK) {
	            throw new RuntimeException("No se pudo actualizar la contraseña.");
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error interno al cambiar la contraseña. Inténtelo más tarde.");
	    }
	}

}
