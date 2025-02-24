<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Acceso Denegado</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
</head>
<body>

	<div class="error-container container mt-5">

		<div class="bg-light p-5 rounded shadow-lg text-center mx-auto mt-2"
			style="max-width: 500px;">
			<h1 class="text-dark"> Error 403 - Acceso Denegado</h1>
			<p>${mensaje}</p>
			<a href="${pageContext.request.contextPath}/inicio/principal"
				class="btn btn-primary">Volver al Inicio</a>
		</div>

	</div>

</body>
</html>
