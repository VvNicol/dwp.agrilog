<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>

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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/estilos/panelAdmin.css">
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

		<img src="<%=request.getContextPath()%>/img/fotos/Agricultura7.jpg"
			alt="Fondo" class="imagen-fondo">


		<div class="container mt-5">
			<div class="card border-success shadow p-4 text-center bg-light">
				<h2 class="text-success">Hola admin</h2>
				<p class="text-muted">Bienvenid@ al panel de administración.</p>
			</div>

			<!-- LISTA DE USUARIOS -->
			<div class="card border-success shadow p-4 text-center bg-light mt-4">
				<h3 class="text-success">Lista de Usuarios</h3>
				<!-- FILTRO DE BÚSQUEDA Y CHECKBOX -->
				<div
					class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
					<input type="text" id="busquedaCorreo"
						class="form-control me-3 mb-2 w-25 input-focus-rojo" minlength="1"
						maxlength="50" placeholder="Buscar por correo...">

					<div>
						<label class="form-check form-check-inline"> <input
							type="checkbox" class="form-check-input" id="filtroUsuario"
							checked> Usuario
						</label> <label class="form-check form-check-inline"> <input
							type="checkbox" class="form-check-input" id="filtroAdmin" checked>
							Admin
						</label>
					</div>
				</div>

				<div class="table-responsive">
					<table class="table table-hover">
						<thead class="table-success bg-success-emphasis text-white">
							<tr>
								<th>Correo</th>
								<th>Rol</th>
								<th>Acción</th>
							</tr>
						</thead>
						<tbody id="tablaUsuarios">
							<%
							String errorUsuarios = (String) request.getAttribute("errorUsuarios");
							@SuppressWarnings("unchecked")
							List<dwp.agrilog.dto.UsuarioDTO> usuarios = (List<dwp.agrilog.dto.UsuarioDTO>) request.getAttribute("usuarios");
							boolean primerAdminNoEliminable = true;
							%>

							<%
							if (errorUsuarios != null) {
							%>
							<tr>
								<td colspan="3" class="text-center text-danger"><%=errorUsuarios%></td>
							</tr>
							<%
							} else if (usuarios != null && !usuarios.isEmpty()) {
							for (dwp.agrilog.dto.UsuarioDTO usuario : usuarios) {
								String correo = usuario.getCorreo();
								String rol = usuario.getRol();
								boolean esAdmin = "ADMIN".equals(rol);

								if (esAdmin && primerAdminNoEliminable) {
									primerAdminNoEliminable = false;
							%>
							<tr class="table-light">
								<td class="td-correo"><%=correo%></td>
								<td class="td-rol"><%=rol%></td>
								<td><button class="btn btn-secondary btn-sm" disabled>No
										eliminable</button></td>
							</tr>
							<%
							} else {
							%>
							<tr>
								<td class="td-correo"><%=correo%></td>
								<td class="td-rol"><%=rol%></td>
								<td
									class="d-flex flex-column flex-sm-row gap-2 justify-content-center">
									<a id="btnLog_<%=correo%>" 
									   href="<%=request.getContextPath()%>/admin/descargar-log?correo=<%=correo%>" 
									   class="btn btn-outline-primary btn-sm"
									   download>
									   Descargar log
									</a>

									<button class="btn btn-danger btn-sm"
										onclick="confirmarEliminacion('<%=correo%>')">Eliminar</button>
								</td>

								<%
								}
								}
								} else {
								%>
							
							<tr>
								<td colspan="3" class="text-center text-muted">No hay
									usuarios registrados.</td>
							</tr>
							<%
							}
							%>
						</tbody>


					</table>
				</div>
			</div>
		</div>
	</main>

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
					<p>
						Estás intentando eliminar a: <strong id="correoUsuario"></strong>
					</p>
					<p>Por favor, escribe el correo electrónico para confirmar:</p>
					<input type="text" class="form-control"
						id="inputCorreoConfirmacion" minlength="1" maxlength="50"
						placeholder="Introduce el correo" required>
					<div id="mensajeErrorCorreo" class="text-danger mt-2"
						style="display: none;">El correo no coincide. Intenta de
						nuevo.</div>
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
	<script>
		const CONTEXTO = '${pageContext.request.contextPath}';
	</script>
	<script src="${pageContext.request.contextPath}/js/admin/adminPanel.js"></script>


</body>
</html>
