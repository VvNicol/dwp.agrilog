$(document).ready(function() {
    $("#registroForm").submit(function(event) {
        event.preventDefault(); // Evita el envío por defecto
        $.ajax({
            url: $(this).attr("action"),
            type: $(this).attr("method"),
            data: $(this).serialize(),
            dataType: "json", // Espera JSON
            success: function(data) {
                if (data.mensaje) {
                    $("#alert-container").html(
                        '<div class="alert alert-success mt-3" role="alert">' + data.mensaje + '</div>'
                    );
                    // Borra los campos del formulario
                    $("#registroForm")[0].reset();
                } else if (data.error) {
                    $("#alert-container").html(
                        '<div class="alert alert-danger mt-3" role="alert">' + data.error + '</div>'
                    );
                }
            },
            error: function(jqXHR) {
                var errorMsg = "";
                try {
                    var jsonResponse = JSON.parse(jqXHR.responseText);
                    errorMsg = jsonResponse.error || "Error en la solicitud.";
                } catch (e) {
                    errorMsg = "Error en la solicitud.";
                }
                $("#alert-container").html(
                    '<div class="alert alert-danger mt-3" role="alert">' + errorMsg + '</div>'
                );
            }
        });
    });
});
