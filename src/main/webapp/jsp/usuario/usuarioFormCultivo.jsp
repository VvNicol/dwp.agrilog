<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Cultivo formulario</title>
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
			<a href="#" class="btn btnNav btnNotificaciones">Panel</a> <a
				href="${pageContext.request.contextPath}/cerrar-sesion"
				class="btn btn-danger btn-sm ms-3">Cerrar Sesión</a>
		</div>
	</nav>

	<div class="container mt-5">



		<div class="row justify-content-center">
			<div class="col-md-8 col-lg-6">
				<div class="mb-3">
					<a href="${pageContext.request.contextPath}/usuario/panel"
						class="btn btn-warning btn-sm"> ← Volver </a>
				</div>
				<div class="card ${modoEdicion ? 'shadow-edicion' : 'shadow-creacion'}">
					<div class="card-body">

						<h4 class="card-title text-center mb-4">
							<c:choose>
								<c:when test="${modoEdicion}">Editar cultivo</c:when>
								<c:otherwise>Crear nuevo cultivo</c:otherwise>
							</c:choose>
						</h4>

						<form id="formCultivo">

					    <!-- Campos ocultos para modo edición -->
					    <c:if test="${modoEdicion}">
					        <input type="hidden" id="cultivoId" value="${cultivo.cultivoId}" />
					        <input type="hidden" id="modoEdicion" value="true" />
					    </c:if>
					    <c:if test="${!modoEdicion}">
					        <input type="hidden" id="modoEdicion" value="false" />
					    </c:if>
					
					    <div class="mb-2">
					        <label for="nombrePlanta" class="form-label">Nombre de la planta</label>
					        <input
					            type="text"
					            class="form-control"
					            id="nombrePlanta"
					            name="nombrePlanta"
					            placeholder="Ej: Tomate cherry"
					            required
					            minlength="2"
					            maxlength="50"
					            value="${cultivo.nombre}"
					            title="Debe tener entre 2 y 100 caracteres.">
					    </div>
					
					   <div class="mb-2">
						    <label for="cantidad" class="form-label">Cantidad</label>
						    <input
						        type="number"
						        class="form-control"
						        id="cantidad"
						        name="cantidad"
						        placeholder="Ej. 50"
						        min="1"
						        max="100000"
						        required
						        oninput="if(this.value.length > 5) this.value = this.value.slice(0, 5);"
						        value="${cultivo.cantidad}"
						        title="Debe ser un número entre 1 y 100000.">
						</div>

					
					    <div class="mb-2">
					        <label for="descripcion" class="form-label">Descripción (opcional)</label>
					        <textarea
					            class="form-control"
					            id="descripcion"
					            name="descripcion"
					            rows="2"
					            maxlength="255"
					            placeholder="Ej: Plantado en zona norte, bajo malla sombra.">${cultivo.descripcion}</textarea>
					    </div>
					
					    <div class="mb-2">
					        <label for="fechaSiembra" class="form-label">Fecha de siembra</label>
					        <input
					            type="date"
					            class="form-control"
					            id="fechaSiembra"
					            name="fechaSiembra"
					            required
					            value="${cultivo.fechaSiembra}"
					            title="Selecciona una fecha válida.">
					    </div>
					
					    <div class="mb-3">
					        <label class="form-label">Parcela <span class="text-danger">*</span></label>
					
					        <input
					            type="text"
					            class="form-control mb-2"
					            id="nuevaParcela"
					            name="nuevaParcela"
					            placeholder="Ej: Parcela norte 1"
					            minlength="1"
					            maxlength="50"
					            oninput="document.getElementById('parcelaExistente').disabled = this.value.trim().length > 0">
					
					        <select
					            class="form-select"
					            id="parcelaExistente"
					            name="parcelaExistente"
					            onchange="document.getElementById('nuevaParcela').disabled = this.value !== ''">
					            <option value="">-- Seleccionar parcela existente --</option>
					            <c:forEach var="parcela" items="${parcelas}">
					                <option value="${parcela.parcelaId}"
					                    <c:if test="${modoEdicion && cultivo.parcelaId.parcelaId == parcela.parcelaId}">selected</c:if>>
					                    ${parcela.nombre}
					                </option>
					            </c:forEach>
					        </select>
					
					        <div class="form-text text-danger d-none" id="parcelaError">
					            Debes seleccionar o escribir una parcela.
					        </div>
					    </div>
					
					    <div class="text-center">
					        <button type="submit" class="btn btn-success px-4">
					            <c:choose>
					                <c:when test="${modoEdicion}">Actualizar</c:when>
					                <c:otherwise>Guardar</c:otherwise>
					            </c:choose>
					        </button>
					    </div>
					
					    <div id="mensajeAjax" class="mt-3 text-center"></div>
					
					</form>

					</div>
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

	<c:if test="${modoEdicion}">
		<script>
			const CULTIVO_DATOS = {
				id : "${cultivo.cultivoId}",
				nombre : "${cultivo.nombre}",
				cantidad : "${cultivo.cantidad}",
				descripcion : `${cultivo.descripcion}`,
				fechaSiembra : "${cultivo.fechaSiembra}",
				parcelaId : "${cultivo.parcelaId.parcelaId}"
			};
			const MODO_EDICION = true;
		</script>
	</c:if>
	<c:if test="${!modoEdicion}">
		<script>
			const MODO_EDICION = false;
		</script>
	</c:if>

	<script
		src="${pageContext.request.contextPath}/js/usuario/usuarioFormCultivojs.js"></script>


</body>
</html>
