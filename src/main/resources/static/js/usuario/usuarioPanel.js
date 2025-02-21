//usuarioPanel.js
$(document).ready(function() {
	var token = localStorage.getItem("jwtToken");

	if (!token) {
		window.location.href = "../../html/inicio/iniciarSesion.jsp";
	}

	$.ajax({
	        url: "http://localhost:8081/proyectoAgricola/html/usuario/usuarioPanel.jsp", // o /admin/panel
	        type: "GET",
	        headers: {
	            "Authorization": "Bearer " + token
	        },
	        error: function(xhr) {
	            if (xhr.status === 401 || xhr.status === 403) {
	                alert("No tienes permiso para acceder aquí.");
	                localStorage.removeItem("jwtToken");
	                window.location.href = "<%=request.getContextPath()%>/html/inicio/iniciarSesion.jsp";
	            }
	        }
	    });
	
	$("#cerrarSesion").click(function() {
		localStorage.removeItem("jwtToken");
		localStorage.removeItem("correoUsuario");
		window.location.href = "../../html/inicio/iniciarSesion.jsp";
	});
});
