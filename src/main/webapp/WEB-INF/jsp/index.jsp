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

<link rel="stylesheet" href="${pageContext.request.contextPath}/estilos/nav.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/estilos/contenidoIndex.css">

<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico" />


<meta charset="UTF-8">
<title>Agrilog</title>
</head>
<body>
	<nav class="navbar">
		<div class="logo-contenedor align-items-center justify-content-center">
			<img src="${pageContext.request.contextPath}/img/logo/Logo-sin-fondo.png" alt="Logo" class="logo" /> 
			<span
				class="logo-text">Agrilog</span>
		</div>

		<div class="buttons-container">

			<a href="${pageContext.request.contextPath}/inicio/" class="btn btn-transparent btnNav"> Inicio </a> <a
				href="${pageContext.request.contextPath}/inicio/iniciar-sesion"
				class="btn btn-transparent btnNav"> Iniciar Sesión </a> <a
				href="${pageContext.request.contextPath}/inicio/registrarse"
				class="btn btn-transparent btnNav"> Registrarse </a>

		</div>
	</nav>
	<main>
		<div class="landing-container">
			<div class="overlay">
				<h1 class="text-white text-center">"El cultivo de hoy es la
					cosecha del mañana."</h1>
				<p class="text-white text-center">Descubre cómo mejorar tu
					producción agrícola con tecnología.</p>
				<a href="html/inicio/iniciarSesion.jsp"
					class="btn btn-success btn-lg">Comenzar</a>
			</div>
		</div>
	</main>



	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
</body>
</html>