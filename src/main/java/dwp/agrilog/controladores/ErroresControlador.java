package dwp.agrilog.controladores;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controlador encargado de manejar los errores HTTP comunes como 404, 500, etc.
 * Muestra páginas personalizadas según el código de estado.
 * 
 * Implementa la interfaz {@link ErrorController} de Spring Boot.
 * 
 * @author nrojlla
 * @date 28052025
 */
@Controller
public class ErroresControlador implements ErrorController {

	/**
	 * Maneja los errores capturados por el servidor y redirige a una vista personalizada
	 * dependiendo del código de error.
	 *
	 * @param request Objeto {@link HttpServletRequest} que contiene los detalles del error
	 * @return Vista correspondiente según el código de estado HTTP detectado
	 */
	@RequestMapping("/error")
	public ModelAndView manejarError(HttpServletRequest request) {
		Object status = request.getAttribute("jakarta.servlet.error.status_code");

		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());

			if (statusCode == 404) {
				return new ModelAndView("errores/error404");
			} else if (statusCode == 400) {
				return new ModelAndView("errores/error400");

			} else if (statusCode == 403) {
				return new ModelAndView("errores/error403");
			} else if (statusCode == 500) {
				return new ModelAndView("errores/error500");
			}
		}

		return new ModelAndView("errores/errorGenerico");
	}
}
