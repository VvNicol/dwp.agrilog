//validarSesion.js
$(document).ready(function () {
    var token = localStorage.getItem("jwtToken");

    if (!token) {
        window.location.href = "iniciarSesion.jsp";
    }
});
