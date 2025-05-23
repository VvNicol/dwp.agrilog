package dwp.agrilog.controladores;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErroresControlador implements ErrorController {

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
