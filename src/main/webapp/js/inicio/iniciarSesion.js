$(document).ready(function() {
	$("#iniciarSesionForm").submit(function(event) {
		event.preventDefault();

		var formData = {
			correo: $("#correo").val(),
			contrasenia: $("#contrasenia").val()
		};

		$.ajax({
			url: "http://localhost:8081/proyectoAgricola/inicio/iniciar-sesion",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify(formData),
			dataType: "json",
			success: function(response) {
				$("#alerta-contenedor").html(
					'<div class="alert alert-success" role="alert">' + response.mensaje + '</div>'
				);
				setTimeout(function() {
					window.location.href = "index.jsp";
				}, 2000);
			},
			error: function(xhr) {
				var errorMessage = "Error al iniciar sesión.";
				if (xhr.responseJSON && xhr.responseJSON.error) {
					errorMessage = xhr.responseJSON.error;
				}
				$("#alerta-contenedor").html(
					'<div class="alert alert-danger" role="alert">' + errorMessage + '</div>'
				);
			}
		});
	});
});
