package dwp.agrilog.filtros;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dwp.agrilog.servicios.FicheroServicio;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Filtro para registrar el recorrido del usuario en la aplicación. Captura las
 * páginas visitadas por cada sesión (autenticada o anónima).
 * 
 * @autor nrojlla 04032025
 */
@Component
public class FicheroFiltro implements Filter {

	@Autowired
	private FicheroServicio ficheroServicio;

	/**
	 * Método que intercepta todas las peticiones HTTP y registra las páginas
	 * visitadas.
	 * 
	 * @param solicitud Solicitud HTTP entrante.
	 * @param response  Respuesta HTTP saliente.
	 * @param chain     Cadena de filtros que permite continuar con la solicitud.
	 */
	@Override
	public void doFilter(ServletRequest solicitud, ServletResponse respuesta, FilterChain chain)
			throws IOException, ServletException {
		if (solicitud instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) solicitud;
			HttpSession session = httpRequest.getSession();
			String urlVisitada = httpRequest.getRequestURI();

			// Registrar la página visitada en los archivos de sesión
			ficheroServicio.registrarEvento(session, urlVisitada, "Página visitada");
		}

		// Continuar con la cadena de filtros para que la solicitud no se interrumpa
		chain.doFilter(solicitud, respuesta);
	}

}
