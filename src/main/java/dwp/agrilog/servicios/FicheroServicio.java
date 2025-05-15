package dwp.agrilog.servicios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

/**
 * Servicio para gestionar los registros de sesiones y eventos de usuarios.
 * Implementa la interfaz FicheroInterfaz.
 * 
 * @autor nrojlla 04032025
 */
@Service
public class FicheroServicio implements FicheroInterfaz {

	// 1. Directorios donde se almacenarán los registros
	//private static final String RUTA_BASE = System.getProperty("user.dir").replaceAll("/+$", "");
	// 1. Ruta base del proyecto desplegado en Tomcat

	private static final String RUTA_BASE = "/opt/tomcat/apache-tomcat-11.0.1/webapps/proyectoAgricola";

	
	private static final String DirectorioUsuario = RUTA_BASE + "/registros/usuarios/";
	private static final String DirectorioAnonimo = RUTA_BASE + "/registros/anonimos/";

	// 2. Extensiones de archivos estáticos para que no se deben registrar
	private static final Set<String> ExtensionesIgnoradas = Set.of(".css", ".js", ".png", ".jpg", ".jpeg", ".gif",
			".svg", ".ico");

	private static final DateTimeFormatter FormatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private static final DateTimeFormatter FormatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

	public void registrarEvento(HttpSession session, String descripcion, String tipoRegistro) {
		if (tipoRegistro.equals("Página visitada") && esArchivoEstatico(descripcion)) {
			return; // Ignorar archivos estáticos
		}

		String usuario = (String) session.getAttribute("usuario");
		String sessionId = session.getId();
		String nombreArchivo;
		String directorio;

		if (usuario != null) {
			// Usuarios autenticados
			directorio = DirectorioUsuario;
			nombreArchivo = "sesion_" + usuario + ".txt";
		} else {
			// Usuarios anonimos
			directorio = DirectorioAnonimo;
			String fechaHoy = LocalDate.now().format(FormatoFecha);
			nombreArchivo = fechaHoy + "_anonimo_" + sessionId + ".txt";
		}

		// Guardar la acción
		guardarEnArchivo(directorio, nombreArchivo, tipoRegistro + ": " + descripcion + " - Usuario: "
				+ (usuario != null ? usuario : "anonimo_" + sessionId));
	}

	/**
	 * Guarda un evento en el archivo de sesión correspondiente.
	 * 
	 * @param directorio    Ruta del directorio donde se guardará el archivo.
	 * @param nombreArchivo Nombre del archivo donde se registrará la acción.
	 * @param contenido     Información a escribir en el archivo.
	 */
	private void guardarEnArchivo(String directorio, String nombreArchivo, String contenido) {
		File carpeta = new File(directorio);
		if (!carpeta.exists()) {
			boolean creada = carpeta.mkdirs();
			if (!creada) {
				throw new RuntimeException("No se pudo crear el directorio: " + directorio);
			}
		}

		File archivo = new File(directorio + nombreArchivo);
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo, true))) {
			String hora = LocalDateTime.now().format(FormatoHora);
			escritor.write(hora + " - " + contenido + "\n");
		} catch (IOException e) {
			throw new RuntimeException("Error al escribir en el archivo: " + archivo.getAbsolutePath(), e);
		}
	}

	/**
	 * Verifica si la URL corresponde a un archivo estático (CSS, JS, imágenes,
	 * etc.).
	 * 
	 * @param url Ruta de la página visitada.
	 * @return `true` si es un archivo estático, `false` si no lo es.
	 */
	private boolean esArchivoEstatico(String url) {
		return ExtensionesIgnoradas.stream().anyMatch(url::endsWith);
	}
}
