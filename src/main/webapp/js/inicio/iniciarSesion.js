//iniciarSesion.js
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
                //token y el rol en localStorage
                localStorage.setItem("jwtToken", response.token);
                localStorage.setItem("rolUsuario", response.rol);

                $("#alerta-contenedor").html(
                    '<div class="alert alert-success" role="alert">' + response.mensaje + '</div>'
                );

                setTimeout(function() {
                    if (response.rol === "ROLE_ADMIN") {
                        window.location.href = "../admin/adminPanel.jsp"; // Redirige al panel de admin
                    } else {
                        window.location.href = "../usuario/usuarioPanel.jsp"; // Redirige al panel de usuario
                    }
                }, 1000);
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
