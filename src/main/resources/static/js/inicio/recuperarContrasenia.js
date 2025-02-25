let correoUsuario = "";

function enviarCorreo() {
	let correo = $("#correo").val();
	if (!correo) {
		$("#correo").addClass("is-invalid");
		return;
	}
	$("#correo").removeClass("is-invalid");

	$.ajax({
		type: "POST",
		url: "/codigo-enviar-correo",
		contentType: "application/json",
		data: JSON.stringify({ correo: correo }),
		success: function(response) {
			correoUsuario = correo;
			$("#mensaje").html('<div class="alert alert-success">' + response.mensaje + '</div>');
			$("#seccionCorreo").hide();
			$("#seccionCodigo").show();
		},
		error: function(xhr) {
			$("#mensaje").html('<div class="alert alert-danger">' + xhr.responseJSON.mensaje + '</div>');
		}
	});
}

function verificarCodigo() {
	let codigo = $("#codigo").val();
	if (!codigo || isNaN(codigo)) {
		$("#codigo").addClass("is-invalid");
		return;
	}
	$("#codigo").removeClass("is-invalid");

	$.ajax({
		type: "POST",
		url: "/codigo-verificar",
		contentType: "application/json",
		data: JSON.stringify({ correo: correoUsuario, codigo: codigo }),
		success: function(response) {
			$("#mensaje").html('<div class="alert alert-success">' + response.mensaje + '</div>');
			$("#seccionCodigo").hide();
			$("#seccionContrasena").show();
		},
		error: function(xhr) {
			$("#mensaje").html('<div class="alert alert-danger">' + xhr.responseJSON.mensaje + '</div>');
		}
	});
}

function cambiarContrasena() {
	let nuevaContrasena = $("#nuevaContrasena").val();
	let confirmarContrasena = $("#confirmarContrasena").val();

	if (nuevaContrasena.length < 6) {
		$("#nuevaContrasena").addClass("is-invalid");
		return;
	}
	$("#nuevaContrasena").removeClass("is-invalid");

	if (nuevaContrasena !== confirmarContrasena) {
		$("#confirmarContrasena").addClass("is-invalid");
		return;
	}
	$("#confirmarContrasena").removeClass("is-invalid");

	$.ajax({
		type: "POST",
		url: "/nueva-contrasenia",
		contentType: "application/json",
		data: JSON.stringify({ correo: correoUsuario, nuevaContrasenia: nuevaContrasena }),
		success: function(response) {
			$("#mensaje").html('<div class="alert alert-success">' + response.mensaje + '</div>');
			$("#seccionContrasena").hide();
		},
		error: function(xhr) {
			$("#mensaje").html('<div class="alert alert-danger">' + xhr.responseJSON.mensaje + '</div>');
		}
	});
}

function volverAlCorreo() {
	$("#seccionCodigo").hide();
	$("#seccionCorreo").show();
}