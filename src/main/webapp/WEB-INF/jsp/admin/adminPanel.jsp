<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Panel de Administrador</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/estilos/nav.css">
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
			<a href="#" class="btn btn-activo btnNav"> Panel </a> <a
				href="${pageContext.request.contextPath}/cerrar-sesion"
				class="btn btn-danger btn-sm ms-3"> Cerrar Sesión </a>

		</div>
	</nav>

<main>

    <img src="<%=request.getContextPath()%>/img/fotos/Agricultura7.jpg" alt="Fondo" class="imagen-fondo">


	<div class="container mt-5">
		<div
			class="card border-success shadow p-4 text-center bg-light">
			<h2 class="text-success">Hola admin</h2>
			<p class="text-muted">Bienvenid@ al panel de administración.</p>
		</div>

		<!-- 📋 LISTA DE USUARIOS -->
		<div class="card border-success shadow p-4 text-center bg-light mt-4">
			<h3 class="text-success">Lista de Usuarios</h3>
			<div class="table-responsive">
				<table class="table table-hover">
					<thead class="table-success bg-success-emphasis text-white">
						<tr>
							<th>Correo</th>
							<th>Rol</th>
							<th>Acción</th>
						</tr>
					</thead>
					<tbody>
						<%
						List<Map<String, Object>> usuarios = (List<Map<String, Object>>) request.getAttribute("usuarios");
						boolean primerAdminNoEliminable = true;

						if (usuarios != null && !usuarios.isEmpty()) {
							for (Map<String, Object> usuario : usuarios) {
								String correo = (String) usuario.get("correo");
								String rol = (String) usuario.get("rol");
								boolean esAdmin = "ADMIN".equals(rol);

								if (esAdmin && primerAdminNoEliminable) {
							primerAdminNoEliminable = false;
						%>
						<tr class="table-light">
							<td><%=correo%></td>
							<td><%=rol%></td>
							<td>
								<button class="btn btn-secondary btn-sm" disabled>No
									eliminable</button>
							</td>
						</tr>
						<%
						} else {
						%>
						<tr>
							<td><%=correo%></td>
							<td><%=rol%></td>
							<td>
								<button class="btn btn-danger btn-sm"
									onclick="confirmarEliminacion('<%=correo%>')">Eliminar</button>
							</td>
						</tr>
						<%}}} else {%>
						<tr>
							<td colspan="3" class="text-center text-danger">No hay usuarios registrados</td>
						</tr><%}%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</main>
	<!-- CONFIRMACIÓN DE ELIMINACIÓN -->
	<script>
		function confirmarEliminacion(correo) {
			let confirmacion = new bootstrap.Modal(document
					.getElementById('modalConfirmacion'));
			document.getElementById('correoUsuario').textContent = correo;
			document.getElementById('confirmarEliminar').onclick = function() {
				let form = document.createElement('form');
				form.method = 'POST';
				form.action = '${pageContext.request.contextPath}/admin/eliminar-usuario';

				let input = document.createElement('input');
				input.type = 'hidden';
				input.name = 'correo';
				input.value = correo;

				form.appendChild(input);
				document.body.appendChild(form);
				form.submit();
			};
			confirmacion.show();
		}
	</script>

	<!-- MODAL DE CONFIRMACIÓN -->
	<div class="modal fade" id="modalConfirmacion" tabindex="-1"
		aria-labelledby="modalConfirmacionLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header bg-danger text-white">
					<h5 class="modal-title" id="modalConfirmacionLabel">⚠️
						Confirmación de Eliminación</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					¿Estás seguro de que deseas eliminar al <strong
						id="correoUsuario"></strong>? <br> Esta acción no se puede
					deshacer.
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancelar</button>
					<button type="button" class="btn btn-danger" id="confirmarEliminar">Eliminar</button>
				</div>
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
