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

	<div class="error-container">
		<h1>Error 403 - Acceso Denegado</h1>
		<p>${mensaje}</p>
		<a href="${pageContext.request.contextPath}/index.jsp"
			class="btn btn-primary">Volver al Inicio</a>
	</div>

</body>
</html>
