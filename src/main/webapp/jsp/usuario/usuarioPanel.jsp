<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	href="<%=request.getContextPath()%>/estilos/nav.css">
<link rel="icon" type="image/x-icon"
	href="<%=request.getContextPath()%>/favicon.ico" />
</head>
<body>
	<nav class="navbar">
		<div class="logo-contenedor align-items-center justify-content-center">
			<img src="<%=request.getContextPath()%>/img/logo/Logo-sin-fondo.png"
				alt="Logo" class="logo" /> <span class="logo-text">Agrilog</span>
		</div>

		<div class="buttons-container">
			<a href="#" class="btn btn-activo btnNav"> Panel </a> <a
				href="${pageContext.request.contextPath}/cerrar-sesion"
				class="btn btn-danger btn-sm ms-3"> Cerrar Sesión </a>
		</div>
	</nav>

	<div class="container mt-5">
		<div class="card shadow p-4 text-center">
			<h2>Hola</h2>
			<p>Bienvenido al panel.</p>
		</div>
		<div class="container mt-5">
			<div class="card shadow p-4">
				<h2 class="text-center mb-3">Crear Parcela</h2>

				<form action="${pageContext.request.contextPath}/parcela/crear"
					method="post">
					<div class="mb-3">
						<label for="nombre" class="form-label">Nombre</label> <input
							type="text" class="form-control" id="nombre" name="nombre"
							required>
					</div>

					<div class="mb-3">
						<label for="descripcion" class="form-label">Descripción</label>
						<textarea class="form-control" id="descripcion" name="descripcion"
							rows="3"></textarea>
					</div>

					<div class="text-center">
						<button type="submit" class="btn btn-success px-4">Crear
							Parcela</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>

</body>
</html>
