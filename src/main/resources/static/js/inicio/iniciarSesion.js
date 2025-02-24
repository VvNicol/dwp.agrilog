$(document).ready(function() {
    $("#registroForm").submit(function(event) {
        event.preventDefault(); // Evita el envío por defecto

        // 🔥 Capturar los datos antes de deshabilitar los campos
        var formData = $(this).serialize();

        // 🔥 Deshabilitar los campos y el botón mientras se procesa la solicitud
        $("#registroForm input, #registroForm button").prop("disabled", true);

        // 🔥 Mostrar mensaje de "Enviando... Espere"
        $("#alerta-contenedor").html(
            '<div class="alert alert-info mt-3" role="alert">Enviando... Espere</div>'
        );

        $.ajax({
            url: $(this).attr("action"),
            type: $(this).attr("method"),
            data: formData,  // ✅ Ahora usamos la variable que ya capturó los datos
            dataType: "json",
            success: function(data) {
                if (data.mensaje) {
                    $("#alerta-contenedor").html(
                        '<div class="alert alert-success mt-3" role="alert">' + data.mensaje + '</div>'
                    );
                    $("#registroForm")[0].reset();
                } else if (data.error) {
                    $("#alerta-contenedor").html(
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
                $("#alerta-contenedor").html(
                    '<div class="alert alert-danger mt-3" role="alert">' + errorMsg + '</div>'
                );
            },
            complete: function() {
                // 🔥 Volver a habilitar los campos después de recibir respuesta
                $("#registroForm input, #registroForm button").prop("disabled", false);
            }
        });
    });
});
