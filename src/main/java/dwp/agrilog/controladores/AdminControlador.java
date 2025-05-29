package dwp.agrilog.controladores;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dwp.agrilog.dto.UsuarioDTO;
import dwp.agrilog.servicios.AdminServicio;
import jakarta.servlet.http.HttpSession;
/**
 * Controlador para admin autenticado.
 * 
 * @autor nrojlla 25022025
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {

	@Autowired
	private AdminServicio adminServicio;

	/**
	 * Muestra el panel de administración si el usuario está autenticado.
	 * 
	 * @param session Sesión HTTP del usuario.
	 * @return Vista del panel de administrador con la lista de usuarios o
	 *         redirección al inicio de sesión.
	 */
	@GetMapping("/panel")
	public ModelAndView mostrarPanelAdmin(HttpSession session) {
		var authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verifica si el usuario está autenticado y si tiene roles asignados
		if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().isEmpty()) {
			return new ModelAndView("redirect:/inicio/iniciar-sesion");
		}

		// Obtener la lista de usuarios desde la API
		List<UsuarioDTO> usuarios = null;
	    String errorUsuarios = null;

	    try {
	        usuarios = adminServicio.obtenerListaUsuarios();
	    } catch (Exception e) {
	        errorUsuarios = "Error al cargar los datos.";
	    }

		ModelAndView modelAndView = new ModelAndView("admin/adminPanel");
		modelAndView.addObject("usuarios", usuarios);
		modelAndView.addObject("errorUsuarios", errorUsuarios);

		return modelAndView;
	}

	/**
	 * Maneja la eliminación de usuarios desde el panel de administración.
	 * 
	 * @param correo Correo del usuario a eliminar.
	 * @return Redirección al panel con la lista de usuarios actualizada.
	 */
	@PostMapping("/eliminar-usuario")
	public ModelAndView eliminarUsuario(@RequestParam String correo) {
		adminServicio.eliminarUsuario(correo);
		return new ModelAndView("redirect:/admin/panel");
	}
	
	private static final String RUTA_BASE = "/opt/tomcat/apache-tomcat-11.0.1/webapps/proyectoAgricola/registros/usuarios/";

	/**
	 * Verifica si existe un archivo de log para un usuario dado.
	 * 
	 * @param correo Correo del usuario, utilizado para construir el nombre del archivo de log.
	 * @return true si el archivo existe, false en caso contrario.
	 */
	@GetMapping("/existe-log")
	@ResponseBody
	public boolean existeLog(@RequestParam String correo) {
		String nombreArchivo = "sesion_" + correo + ".txt";
		File archivo = new File("/opt/tomcat/apache-tomcat-11.0.1/webapps/proyectoAgricola/registros/usuarios/" + nombreArchivo);
		return archivo.exists();
	}

	/**
	 * Permite descargar el archivo de log correspondiente a un usuario.
	 * 
	 * @param correo Correo del usuario cuyo archivo de log se desea descargar.
	 * @return ResponseEntity con el archivo como recurso descargable si existe, o un mensaje de error si no.
	 */
	@GetMapping("/descargar-log")
	public ResponseEntity<?> descargarLog(@RequestParam String correo) {
		String nombreArchivo = "sesion_" + correo + ".txt";
		File archivo = new File(RUTA_BASE + nombreArchivo);

		if (!archivo.exists()) {
			return ResponseEntity.status(404).body("El archivo de log no existe.");
		}

		try {
			InputStreamResource recurso = new InputStreamResource(new FileInputStream(archivo));
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivo)
					.contentType(MediaType.TEXT_PLAIN)
					.contentLength(Files.size(archivo.toPath()))
					.body(recurso);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Error al procesar el archivo.");
		}
	}

}
