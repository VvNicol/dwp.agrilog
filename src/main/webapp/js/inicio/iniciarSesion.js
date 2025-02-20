$(document).ready(function () {
    $("#iniciarSesionForm").submit(function (event) {
        event.preventDefault();

        var formData = {
            correo: $("#correo").val(),
            contrasenia: $("#contrasenia").val()
        };

        $.ajax({
            url: "http://localhost:8081/proyectoAgricola/inicio/iniciar-sesion", // Ruta corregida
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(formData), // ✅ Enviamos solo el JSON sin token
            dataType: "json",
            success: function (response) {
                // ✅ Guardar el token y el rol después de recibir la respuesta
                localStorage.setItem("jwtToken", response.token);
                localStorage.setItem("rolUsuario", response.rol);

                // ✅ Mensaje de éxito
                $("#alerta-contenedor").html(
                    '<div class="alert alert-success" role="alert">' + response.mensaje + '</div>'
                );

                // ✅ Redirigir según el rol
                setTimeout(function () {
                    if (response.rol === "ROLE_ADMIN") {
                        window.location.href = "../admin/adminPanel.jsp"; 
                    } else {
                        window.location.href = "../usuario/usuarioPanel.jsp"; 
                    }
                }, 1000);
            },
            error: function (xhr) {
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
