package dwp.agrilog.excepciones;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GestorGlobalDeExcepciones {
    
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView manejarAccesoDenegado(AccessDeniedException ex) {
        ModelAndView modelAndView = new ModelAndView("/html/errores/error403");
        modelAndView.addObject("mensaje", "No tienes permisos para acceder a esta página.");
        return modelAndView;
    }
    
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView manejarError404(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("/html/errores/error404");
        modelAndView.addObject("mensaje", "La página que buscas no existe.");
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView manejarError500(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("/html/errores/error500");
        modelAndView.addObject("mensaje", "Ocurrió un error inesperado. Inténtalo más tarde.");
        return modelAndView;
    }
}
