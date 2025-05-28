package dwp.agrilog.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dwp.agrilog.dto.CultivoDto;
import dwp.agrilog.dto.NotificacionDto;
import dwp.agrilog.dto.ParcelaDto;
import dwp.agrilog.seguridad.UsuarioAutenticado;
import dwp.agrilog.servicios.CultivoServicio;
import dwp.agrilog.servicios.NotificacionServicio;
import dwp.agrilog.servicios.ParcelaServicio;
import jakarta.servlet.http.HttpSession;

/**
 * Controlador para el usuario autenticado.
 * 
 * @autor nrojlla 25022025
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

	@Autowired
	private CultivoServicio cultivoServicio;

	@Autowired
	private ParcelaServicio parcelaServicio;
	
	@Autowired
	private NotificacionServicio notificacionServicio;

	/**
	 * Muestra el panel de usuario si está autenticado.
	 * 
	 * @param session Sesión HTTP del usuario.
	 * @return Vista del panel de usuario o redirección al inicio de sesión.
	 */
	@GetMapping("/panel")
	public ModelAndView mostrarPanelUsuario(HttpSession session) {

	    var authentication = SecurityContextHolder.getContext().getAuthentication();

	    // Verifica si el usuario está autenticado y si tiene roles asignados
	    if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().isEmpty()) {
	        return new ModelAndView("redirect:/inicio/iniciar-sesion");
	    }

	    UsuarioAutenticado usuario = (UsuarioAutenticado) authentication.getPrincipal();
	    Long usuarioId = usuario.getId();

	    ModelAndView mav = new ModelAndView("usuario/usuarioPanel");

	    try {
	        List<ParcelaDto> parcelas = parcelaServicio.obtenerParcelasPorUsuario(usuarioId);
	        List<CultivoDto> cultivos = cultivoServicio.obtenerCultivosPorUsuario(usuarioId);
	        List<NotificacionDto> notificaciones = notificacionServicio.obtenerPorUsuario(usuarioId);

	        int totalPlantas = cultivos.stream()
	                                   .mapToInt(CultivoDto::getCantidad)
	                                   .sum();

	        // Evitar error al convertir LocalDateTime a JSON
	        for (NotificacionDto noti : notificaciones) {
	            noti.setFechaMensaje(null);
	        }

	        // Convertir notificaciones a JSON
	        ObjectMapper mapper = new ObjectMapper();
	        String notificacionesJSON = "[]";
	        try {
	            notificacionesJSON = mapper.writeValueAsString(notificaciones);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }

	        // Agregar atributos al modelo
	        mav.addObject("parcelas", parcelas);
	        mav.addObject("cultivos", cultivos);
	        mav.addObject("totalPlantas", totalPlantas); 
	        mav.addObject("usuario", usuario);
	        mav.addObject("notificaciones", notificaciones); 
	        mav.addObject("notificacionesJSON", notificacionesJSON);

	    } catch (Exception e) {
	        e.printStackTrace();
	        mav.addObject("error", "No se pudieron cargar los datos del panel.");
	        mav.addObject("parcelas", List.of());
	        mav.addObject("cultivos", List.of());
	        mav.addObject("totalPlantas", 0);
	        mav.addObject("notificaciones", List.of());
	        mav.addObject("notificacionesJSON", "[]");
	    }

	    return mav;
	}


}
