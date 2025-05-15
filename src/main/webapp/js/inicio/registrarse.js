$(document).ready(function () {
    $("#registroForm").submit(function (event) {
        event.preventDefault();

        const data = {
            nombreCompleto: $("#nombreCompleto").val(),
            correo: $("#correo").val(),
            telefono: $("#telefono").val(),
            contrasenia: $("#contrasenia").val()
        };

        $("#registroForm input, #registroForm button").prop("disabled", true);
        $("#alerta-contenedor").html(
            '<div class="alert alert-info mt-3" role="alert">Enviando... Espere</div>'
        );

        $.ajax({
            url: $(this).attr("action"),
            type: $(this).attr("method"),
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: "json",
            success: function (data) {
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
            error: function (jqXHR) {
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
            complete: function () {
                $("#registroForm input, #registroForm button").prop("disabled", false);
            }
        });
    });
});
