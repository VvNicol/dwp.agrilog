package dwp.agrilog.seguridad;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class JwtFiltro extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // Rutas públicas que no requieren autenticación
        if (requestURI.startsWith("/proyectoAgricola/inicio/iniciar-sesion")
                || requestURI.startsWith("/proyectoAgricola/inicio/")
                || requestURI.startsWith("/proyectoAgricola/inicio/registrarse")
                || requestURI.startsWith("/proyectoAgricola/inicio/verificar-correo")
                || requestURI.startsWith("/proyectoAgricola/estilos")
                || requestURI.startsWith("/proyectoAgricola/index")
                || requestURI.startsWith("/proyectoAgricola/img")
                || requestURI.startsWith("/proyectoAgricola/js")) {
            chain.doFilter(request, response);
            return;
        }

        // 🔥 Verificar si el usuario está autenticado en SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("✅ Usuario autenticado en SecurityContextHolder: " + authentication);
            chain.doFilter(request, response); // Permitir el acceso
            return;
        }

        // 🔥 Verificar si hay una sesión activa (opcional, solo si necesitas compatibilidad con sesiones)
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            System.out.println("✅ Usuario autenticado por sesión: " + session.getAttribute("usuario"));
            System.out.println("✅ Rol en sesión: " + session.getAttribute("rol")); // Verifica el rol
            chain.doFilter(request, response);
            return;
        }

        // ❌ Si no hay autenticación válida, denegar acceso
        System.out.println("❌ Acceso denegado: no hay sesión ni token.");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("Acceso denegado: no hay sesión ni token.");
    }
}