$(document).ready(function() {
	var token = localStorage.getItem("jwtToken");

	if (!token) {
		window.location.href = "../../html/inicio/iniciarSesion.jsp";
	}

	$("#cerrarSesion").click(function() {
		localStorage.removeItem("jwtToken");
		localStorage.removeItem("correoUsuario");
		window.location.href = "../../html/inicio/iniciarSesion.jsp";
	});
});
