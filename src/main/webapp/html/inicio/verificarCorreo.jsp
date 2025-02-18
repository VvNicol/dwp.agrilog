<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Verificar Correo</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
  <div class="container mt-5">
    <h2>Verificación de Correo</h2>
    <div id="verificacion-mensaje" class="mt-3"></div>
  </div>
  
  <!-- jQuery (necesario para la llamada AJAX) -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script>
    $(document).ready(function(){
      // Obtener el token desde la URL (por ejemplo: ?token=...)
      var params = new URLSearchParams(window.location.search);
      var token = params.get('token');
      
      if(token){
          // Llamar al API para verificar el token
          $.ajax({
              url: '<%=request.getContextPath()%>/api/verificar-correo?token=' + token,
              type: 'GET',
              dataType: 'json',
              success: function(data){
                // Se espera que el API retorne un JSON con la clave "mensaje"
                if(data.mensaje){
                  $("#verificacion-mensaje").html('<div class="alert alert-success" role="alert">' + data.mensaje + '</div>');
                } else {
                  $("#verificacion-mensaje").html('<div class="alert alert-danger" role="alert">No se pudo verificar el correo.</div>');
                }
              },
              error: function(){
                  $("#verificacion-mensaje").html('<div class="alert alert-danger" role="alert">Error al verificar el correo.</div>');
              }
          });
      } else {
          $("#verificacion-mensaje").html('<div class="alert alert-warning" role="alert">Token no proporcionado.</div>');
      }
    });
  </script>
</body>
</html>
