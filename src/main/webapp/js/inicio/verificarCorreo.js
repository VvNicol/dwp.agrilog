$(document).ready(function() {
    var params = new URLSearchParams(window.location.search);
    var token = params.get('token');

    if (token) {
        $.ajax({
            url: 'https://agrilog.nicoldev.esinicio/verificar-correo?token=' + token,
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                if (data.mensaje) {
                    $("#verificacion-mensaje").html('<div class="alert alert-success" role="alert">' + data.mensaje + '</div>');
                } else {
                    $("#verificacion-mensaje").html('<div class="alert alert-danger" role="alert">No se pudo verificar el correo.</div>');
                }
            },
            error: function(xhr) {
                console.log("Error:", xhr); 
                $("#verificacion-mensaje").html('<div class="alert alert-danger" role="alert">Error al verificar el correo.</div>');
            }
        });
    } else {
        $("#verificacion-mensaje").html('<div class="alert alert-warning" role="alert">Token no proporcionado.</div>');
    }
});
