package dwp.agrilog.controladores;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class ErrorControlador implements ErrorController {

	@GetMapping
	public String manejarErrores(HttpServletRequest request, Model model) {
		Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

		if (statusCode != null) {
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				model.addAttribute("mensaje", "La página que buscas no existe.");
				return "errores/error404.jsp";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				model.addAttribute("mensaje", "No tienes permisos para acceder a esta página.");
				return "errores/error403.jsp";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				model.addAttribute("mensaje", "Ocurrió un error inesperado. Inténtalo más tarde.");
				return "errores/error500.jsp";
			}
		}
		model.addAttribute("mensaje", "Error desconocido.");
		return "errores/error500";
	}

}
