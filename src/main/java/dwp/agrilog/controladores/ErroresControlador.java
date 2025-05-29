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
                ModelAndView mav = new ModelAndView("errores/error404");
                mav.addObject("mensaje", "La página solicitada no fue encontrada.");
                return mav;
            } else if (statusCode == 400) {
                ModelAndView mav = new ModelAndView("errores/error400");
                mav.addObject("mensaje", "Solicitud incorrecta.");
                return mav;
            } else if (statusCode == 403) {
                ModelAndView mav = new ModelAndView("errores/error403");
                mav.addObject("mensaje", "No tienes permiso para acceder a esta página.");
                return mav;
            } else if (statusCode == 500) {
                ModelAndView mav = new ModelAndView("errores/error500");
                mav.addObject("mensaje", "Ocurrió un error interno en el servidor.");
                return mav;
            }
        }

        ModelAndView mav = new ModelAndView("errores/errorGenerico");
        mav.addObject("mensaje", "Se produjo un error inesperado.");
        return mav;
    }
}
