package dwp.agrilog.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dwp.agrilog.dto.UsuarioDTO;
import dwp.agrilog.seguridad.UsuarioAutenticado;
import dwp.agrilog.servicios.FicheroServicio;
import dwp.agrilog.servicios.InicioServicio;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador para la gestión de inicio de sesión y verificación de correo.
 * 
 * @autor nrojlla 25022025
 */
@Controller
@RequestMapping("/inicio")
public class InicioControlador {

	@Autowired
	private InicioServicio inicioServicio;

	@Autowired
	private FicheroServicio ficheroServicio;

	@GetMapping("/")
	public String inicio() {
		return "redirect:/index.jsp";
	}

	/**
	 * Muestra la página principal de la aplicación.
	 *
	 * @return Vista de la página de inicio.
	 */
	@GetMapping("/principal")
	public ModelAndView mostrarIndex() {
		return new ModelAndView("index");
	}

	/**
	 * Muestra la página de inicio de sesión.
	 *
	 * @return Vista de inicio de sesión.
	 */
	@GetMapping("/iniciar-sesion")
	public ModelAndView mostrarInicioSesion() {
		return new ModelAndView("inicio/iniciarSesion");
	}

	/**
	 * Verifica un correo electrónico a través de un token de la url.
	 *
	 * @param token Token de verificación recibido por correo.
	 * @return Vista con mensaje de éxito o error según la verificación.
	 */
	@GetMapping("/verificar-correo")
	public ModelAndView verificarCorreo(@RequestParam("token") String token) {
		ModelAndView modelAndView = new ModelAndView("inicio/verificarCorreo");

		try {
			boolean verificado = inicioServicio.verificarCorreo(token); // Llama al servicio

			if (verificado) {
				modelAndView.addObject("mensaje", "Correo verificado exitosamente. Ya puedes iniciar sesión.");
			} else {
				modelAndView.addObject("error", "El token es inválido o ha expirado.");
			}
		} catch (Exception e) {
			modelAndView.addObject("error", "Ha ocurrido un error inesperado. Intenta nuevamente.");
		}

		return modelAndView;
	}

	/**
	 * Maneja el proceso de inicio de sesión de un usuario.
	 *
	 * @param correo      Correo del usuario.
	 * @param contrasenia Contraseña del usuario.
	 * @param session     Sesión HTTP del usuario.
	 * @param request     Solicitud HTTP.
	 * @param response    Respuesta HTTP.
	 * @return Redirección a la vista según el rol o mensaje de error.
	 * 
	 */
	@PostMapping("/iniciar-sesion")
	public ModelAndView iniciarSesion(@RequestParam String correo, @RequestParam String contrasenia,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		try {
			UsuarioDTO usuario = new UsuarioDTO(correo, contrasenia);
			Map<String, String> resultado = inicioServicio.iniciarSesionUsuario(usuario);

			if (resultado.containsKey("token")) {

				// 1. Almacena datos de sesión
				session.setAttribute("usuario", correo);
				session.setAttribute("rol", resultado.get("rol"));
				session.setAttribute("token", resultado.get("token"));

				ficheroServicio.registrarEvento(session, "Usuario inició sesión", " ");

				// 2. Autenticación con UsuarioAutenticado personalizado
				List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(resultado.get("rol")));
				Long id = Long.parseLong(resultado.get("id"));
				UsuarioAutenticado usuarioAutenticado = new UsuarioAutenticado(
					    id,
					    correo,
					    resultado.get("rol")
					);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						usuarioAutenticado, null, authorities);

				SecurityContext context = SecurityContextHolder.createEmptyContext();
				context.setAuthentication(authentication);
				SecurityContextHolder.setContext(context);

				// 3. Guarda el contexto de seguridad en la sesión
				SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
				securityContextRepository.saveContext(context, request, response);

				return verificarYRedireccionar(resultado.get("rol"));
			} else {
				return new ModelAndView("inicio/iniciarSesion").addObject("error", "Correo o contraseña incorrectos.");
			}
		} catch (Exception e) {
			return new ModelAndView("inicio/iniciarSesion").addObject("error",
					"Error al iniciar sesión: " + e.getMessage());
		}
	}

	/**
	 * Verifica el rol del usuario y redirige a la vista correspondiente.
	 *
	 * @param rol Rol del usuario autenticado.
	 * @return Redirección a la vista del panel correspondiente o mensaje de error.
	 */
	private ModelAndView verificarYRedireccionar(String rol) {
		if ("ADMIN".equals(rol)) {
			return new ModelAndView("redirect:/admin/panel");
		} else if ("USUARIO".equals(rol)) {
			return new ModelAndView("redirect:/usuario/panel");
		} else {
			return new ModelAndView("inicio/iniciarSesion").addObject("error", "Rol desconocido.");
		}
	}

}
