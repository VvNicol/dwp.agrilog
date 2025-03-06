<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Agrilog - recuperar contraseña</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/estilos/nav.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/estilos/mainRecuperarContrasenia.css">
<link rel="icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/favicon.ico" />
</head>
<body>
	<nav class="navbar">
		<div class="logo-contenedor align-items-center justify-content-center">
			<img
				src="${pageContext.request.contextPath}/img/logo/Logo-sin-fondo.png"
				alt="Logo" class="logo" /> <span class="logo-text">Agrilog</span>
		</div>
		<div class="buttons-container">
			<a href="${pageContext.request.contextPath}/inicio/principal"
				class="btn btn-transparent btnNav">Inicio</a> <a
				href="${pageContext.request.contextPath}/inicio/iniciar-sesion"
				class="btn btn-transparent btnNav">Iniciar Sesión</a> <a
				href="${pageContext.request.contextPath}/regis/registrarse"
				class="btn btn-transparent btnNav">Registrarse</a>
		</div>
	</nav>
	<main>
		<div class="container mt-5 p-5">
			<div class="row justify-content-center mt-5">
				<div class="col-12 col-md-6 bg-light rounded shadow-lg col-lg-4 p-4">

					<!-- Sección de correo -->
					<div id="seccionCorreo">
						<h4 class="mb-3 text-center">Recuperación de Contraseña</h4>
						<form id="formCorreo">
							<div class="mb-4">
								<label for="correo" class="form-label">Ingresa tu correo
									electrónico:</label> <input type="email" id="correo"
									class="form-control" required>
								<div class="invalid-feedback">El correo electrónico es
									obligatorio o no es válido.</div>
							</div>

							<button type="button" class="btn w-100 text-white"
								style="background-color: #5ec762;" onclick="enviarCorreo()">
								Enviar Correo</button>
						</form>
					</div>

					<!-- Sección de código -->
					<div id="seccionCodigo" style="display: none;">
						<h4 class="mb-3 text-center">Verificación de Código</h4>
						<form id="formCodigo">
							<div class="mb-4">
								<label for="codigo" class="form-label">Código</label> <input
									type="text" id="codigo" class="form-control" required>
								<div class="invalid-feedback">Solo se permiten números en
									este campo.</div>
							</div>

							<button type="button" class="btn w-100 text-white"
								style="background-color: #5ec762;" onclick="verificarCodigo()">
								Verificar Código</button>

							<button type="button" class="btn btn-secondary w-100 mt-2"
								onclick="volverAlCorreo()">Volver</button>
						</form>
					</div>

					<!-- Sección de nueva contraseña -->
					<div id="seccionContrasena" style="display: none;">
						<h4 class="mb-3 text-center">Nueva Contraseña</h4>
						<form id="formNuevaContrasena">
							<div class="mb-4">
								<label for="nuevaContrasena" class="form-label">Nueva
									Contraseña</label> <input type="password" id="nuevaContrasena"
									class="form-control" required minlength="6">
								<div class="invalid-feedback">La contraseña debe tener al
									menos 6 caracteres.</div>
							</div>

							<div class="mb-4">
								<label for="confirmarContrasena" class="form-label">Confirmar
									Contraseña</label> <input type="password" id="confirmarContrasena"
									class="form-control" required>
								<div class="invalid-feedback">Las contraseñas no
									coinciden.</div>
							</div>

							<button type="button" class="btn w-100 text-white"
								style="background-color: #5ec762;" onclick="cambiarContrasena()">
								Enviar</button>
						</form>
					</div>

					<!-- Mensajes -->
					<div id="mensaje" class="mt-3 text-center"></div>

				</div>
			</div>
		</div>
	</main>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>

	<script
		src="${pageContext.request.contextPath}/js/inicio/recuperarContrasenia.js"></script>
</body>
</html>
