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

@Component
public class FicheroFiltro implements Filter {

	@Autowired
	private FicheroServicio ficheroServicio;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpSession session = httpRequest.getSession();
			String urlVisitada = httpRequest.getRequestURI();

			ficheroServicio.registrarEvento(session, urlVisitada, "Página visitada");
		}

		chain.doFilter(request, response);
	}

}
