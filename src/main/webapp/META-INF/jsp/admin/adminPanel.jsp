<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Panel de Usuario</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/estatico/estilos/nav.css">
<link rel="icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/favicon.ico" />
</head>
<body>
	<nav class="navbar">
		<div class="logo-contenedor align-items-center justify-content-center">
			<img src="${pageContext.request.contextPath}/estatico/img/logo/Logo-sin-fondo.png"
				alt="Logo" class="logo" /> <span class="logo-text">Agrilog</span>
		</div>

		<div class="buttons-container">
			<a href="#" class="btn btn-transparent btnNav"> Panel </a> <a
				href="#" class="btn btn-transparent btnNav"> Perfil </a>
			<button class="btn btn-danger btn-sm ms-3" id="cerrarSesion">Cerrar
				Sesión</button>
		</div>
	</nav>

	<div class="container mt-5">
		<div class="card shadow p-4 text-center">
			<h2>Hola admin</h2>
			<p>Bienvenido al panel.</p>
		</div>
	</div>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/js/seguridad/validarSesion.js"></script>
	<script src="${pageContext.request.contextPath}/js/usuario/usuarioPanel.js"></script>
	<script src="${pageContext.request.contextPath}/js/seguridad/proteccionRutas.js"></script>
	
</body>
</html>
