<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>404 - Página No Encontrada</title>

<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">

<!-- Estilos personalizados -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/estilos/error.css">
</head>
<body class="d-flex align-items-center justify-content-center">

	<div class="container text-center mt-5">
		<div class="error-container">
			<h1>Error 404</h1>
			<p>${mensaje}</p>
			<a href="${pageContext.request.contextPath}/inicio/principal"
				class="btn btn-warning bg- mt-3">Volver al Inicio</a>
		</div>
	</div>


</body>
</html>
