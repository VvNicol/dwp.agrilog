<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link rel="stylesheet" href="estilos/nav.css">

<link rel="icon" type="image/x-icon" href="favicon.ico" />


<meta charset="UTF-8">
<title>Agrilog</title>
</head>
<body>
	<nav class="navbar">
		<div class="logo-contenedor align-items-center justify-content-center">
			<img src="img/logo/Logo-sin-fondo .png" alt="Logo" class="logo" /> <span
				class="logo-text">Agrilog</span>
		</div>


		<div class="buttons-container">
			<!-- Suponiendo que tienes una variable 'currentRoute' disponible en el ámbito request o session -->
			<a href="inicio.jsp"
				class="btn btn-transparent btnNav 
      ${currentRoute == '/inicio' ? 'active' : ''}">
				Inicio </a> <a href="iniciar-sesion.jsp"
				class="btn btn-transparent btnNav 
      ${currentRoute == '/iniciar-sesion' ? 'active' : ''}">
				Iniciar Sesión </a> <a href="registrarse.jsp"
				class="btn btn-transparent btnNav 
      ${currentRoute == '/registrarse' ? 'active' : ''}">
				Registrarse </a>
		</div>
	</nav>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>

</body>
</html>