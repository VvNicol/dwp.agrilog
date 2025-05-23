let correoUsuario = "";

function enviarCorreo() {
	let correo = $("#correo").val();

	if (!correo) {
		$("#correo").addClass("is-invalid");
		return;
	}
	$("#correo").removeClass("is-invalid");

	correoUsuario = correo;

	let datos = JSON.stringify({ correo: correo });

	$("#mensaje").html('<div class="alert alert-info">Enviando...</div>');
	$("button").prop("disabled", true);

	$.ajax({
		type: "POST",
		url: "/form/codigo-enviar-correo",
		contentType: "application/json",
		data: datos,
		success: function(response) {
			let mensaje = response.mensaje || "Código enviado correctamente.";
			$("#mensaje").html('<div class="alert alert-success">' + mensaje + '</div>');
			$("#seccionCorreo").hide();
			$("#seccionCodigo").show();
		},
		error: function(xhr) {
			let errorMsg = "Error al enviar el código.";
			if (xhr.responseJSON && xhr.responseJSON.error) {
				errorMsg = xhr.responseJSON.error;
			}
			$("#mensaje").html('<div class="alert alert-danger">' + errorMsg + '</div>');
		},
		complete: function() {
			$("button").prop("disabled", false);
		}
	});
}



function verificarCodigo() {
	let codigo = $("#codigo").val().trim();

	if (!codigo || isNaN(codigo)) {
		$("#codigo").addClass("is-invalid");
		return;
	}
	$("#codigo").removeClass("is-invalid");

	let datos = {
		correo: correoUsuario,
		codigo: codigo.toString()
	};

	$.ajax({
		type: "POST",
		url: "/form/codigo-verificar",
		contentType: "application/json",
		data: JSON.stringify(datos),
		success: function(response) {
			$("#mensaje").html('<div class="alert alert-success">' + response.mensaje + '</div>');
			$("#seccionCodigo").hide();
			$("#seccionContrasena").show();
		},
		error: function(xhr) {
			$("#mensaje").html('<div class="alert alert-danger">' + xhr.responseJSON.mensaje + xhr.responseJSON.error + '</div>');

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

	let datos = {
	        correo: correoUsuario,
	        nuevaContrasenia: nuevaContrasena
	    };

	    $.ajax({
	        type: "POST",
	        url: "/form/nueva-contrasenia",
	        contentType: "application/json",
	        data: JSON.stringify(datos),
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