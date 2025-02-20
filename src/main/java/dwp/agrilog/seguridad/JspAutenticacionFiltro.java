package dwp.agrilog.seguridad;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class JspAutenticacionFiltro implements Filter {

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Verificar si hay un usuario autenticado en la sesión
        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            // Si no hay usuario autenticado, redirigir a la página de inicio de sesión
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/html/inicio/iniciarSesion.jsp");
            return;
        }

        // Si hay usuario autenticado, continuar con la solicitud
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

}
