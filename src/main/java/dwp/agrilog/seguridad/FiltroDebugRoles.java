package dwp.agrilog.seguridad;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FiltroDebugRoles extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        System.out.println("🛠️ [FiltroDebugRoles] Verificando acceso a: " + requestURI);

        // ✅ Permitir rutas públicas sin autenticación
        if (requestURI.startsWith("/proyectoAgricola/inicio/")
                || requestURI.startsWith("/proyectoAgricola/estilos")
                || requestURI.startsWith("/proyectoAgricola/img")
                || requestURI.startsWith("/proyectoAgricola/js")
                || requestURI.endsWith("/favicon.ico")) {
            System.out.println("🔓 Acceso permitido sin autenticación a " + requestURI);
            chain.doFilter(request, response);
            return;
        }

        // 🔥 Evaluar autenticación en rutas protegidas
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().isEmpty()) {
            System.out.println("🚫 [FiltroDebugRoles] Usuario NO autenticado. Bloqueando acceso a: " + requestURI);
            response.sendRedirect(request.getContextPath() + "/inicio/iniciar-sesion");
            return;
        }

        System.out.println("✅ [FiltroDebugRoles] Usuario autenticado: " + authentication.getName());
        System.out.println("🔍 [FiltroDebugRoles] Roles asignados: " + authentication.getAuthorities());

        chain.doFilter(request, response);
    }
}
