package dwp.agrilog.seguridad;

import java.io.IOException;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.context.SecurityContextHolder;

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
        Object usuario = (session != null) ? session.getAttribute("usuario") : null;

        // También verificamos si hay un usuario autenticado en el contexto de seguridad
        boolean tieneToken = SecurityContextHolder.getContext().getAuthentication() != null &&
                             SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User;

        if (usuario == null && !tieneToken) {
            System.out.println("⚠️ No hay sesión ni token válido. Redirigiendo...");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/inicio/iniciar-sesion");
            return;
        }

        // Si hay usuario autenticado, continuar con la solicitud
        System.out.println("✅ Usuario autenticado, permitiendo acceso.");
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

}
