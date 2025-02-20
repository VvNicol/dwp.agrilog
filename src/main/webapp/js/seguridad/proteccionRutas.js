//proteccionRutas.js
$(document).ready(function() {
    var token = localStorage.getItem("jwtToken");
    var rol = localStorage.getItem("rolUsuario");

    if (!token) {
        // Si no hay token, redirige al login
        window.location.href = "../../html/inicio/iniciarSesion.jsp";
    } else {
        var pagina = window.location.pathname;

        // Verificar si está intentando entrar a admin
        if (pagina.includes("adminPanel.jsp") && rol !== "ROLE_ADMIN") {
            window.location.href = "../errores/error403.jsp"; // Redirigir a error
        }

        // Verificar si está intentando entrar a usuario
        if (pagina.includes("usuarioPanel.jsp") && rol !== "ROLE_USUARIO") {
            window.location.href = "../errores/error403.jsp"; // Redirigir a error
        }
    }

    // Cerrar sesión
    $("#cerrarSesion").click(function() {
        localStorage.removeItem("jwtToken");
        localStorage.removeItem("rolUsuario");
        window.location.href = "../../html/inicio/iniciarSesion.jsp";
    });
});
