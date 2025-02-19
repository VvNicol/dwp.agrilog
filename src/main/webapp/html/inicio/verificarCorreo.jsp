<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Verificar Correo</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/estilos/verificarCorreo.css">
<link rel="icon" type="image/x-icon" href="favicon.ico" />

</head>
<body>

	<div class="container mt-5">
		<div class="bg-light p-5 rounded shadow-lg text-center mx-auto"
			style="max-width: 500px;">
			<h2 class="text-dark">Verificación de Correo</h2>
			<div id="verificacion-mensaje" class="mt-3"></div>
			<a href="<%=request.getContextPath()%>/index.jsp"
				class="btn btn-secondary mt-3">Ir al Inicio</a>
		</div>
	</div>


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>

	<script
		src="<%=request.getContextPath()%>/js/inicio/verificarCorreo.js"></script>
</body>
</html>
