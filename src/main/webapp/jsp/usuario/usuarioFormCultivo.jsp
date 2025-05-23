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
			<a href="/usuario/panel" class="btn btnNav"> Panel </a> <a
				href="${pageContext.request.contextPath}/cerrar-sesion"
				class="btn btn-danger btn-sm ms-3"> Cerrar Sesión </a>
		</div>
	</nav>

	<div class="container mt-5">



		<div class="row justify-content-center">
			<div class="col-md-8 col-lg-6">
				<div class="mb-3">
					<a href="${pageContext.request.contextPath}/usuario/panel"
						class="btn btn-warning btn-sm"> ← Volver </a>
				</div>
				<div class="card shadow">
					<div class="card-body">
						<h4 class="card-title text-center mb-4">Crear nuevo cultivo</h4>

						<form id="formCultivo">
							<div class="mb-2">
								<label for="nombrePlanta" class="form-label">Nombre de
									la planta</label> <input type="text" class="form-control"
									id="nombrePlanta" name="nombrePlanta" required minlength="2"
									maxlength="100">
							</div>

							<div class="mb-2">
								<label for="cantidad" class="form-label">Cantidad</label> <input
									type="number" class="form-control" id="cantidad"
									name="cantidad" min="1" required>
							</div>

							<div class="mb-2">
								<label for="descripcion" class="form-label">Descripción
									(opcional)</label>
								<textarea class="form-control" id="descripcion"
									name="descripcion" rows="2" maxlength="255"
									placeholder="Máximo 255 caracteres..."></textarea>
							</div>

							<div class="mb-2">
								<label for="fechaSiembra" class="form-label">Fecha de
									siembra</label> <input type="date" class="form-control"
									id="fechaSiembra" name="fechaSiembra" required>
							</div>

							<div class="mb-3">
								<label class="form-label">Parcela <span
									class="text-danger">*</span></label> <input type="text"
									class="form-control mb-2" id="nuevaParcela" name="nuevaParcela"
									placeholder="Escribe una nueva parcela"
									oninput="document.getElementById('parcelaExistente').disabled = this.value.trim().length > 0">

								<select class="form-select" id="parcelaExistente"
									name="parcelaExistente"
									onchange="document.getElementById('nuevaParcela').disabled = this.value !== ''">
									<option value="">-- Seleccionar parcela existente --</option>
									<c:forEach var="parcela" items="${parcelas}">
										<option value="${parcela.parcelaId}">${parcela.nombre}</option>
									</c:forEach>
								</select>
								<div class="form-text text-danger d-none" id="parcelaError">⚠️
									Debes seleccionar o escribir una parcela.</div>
							</div>

							<div class="text-center">
								<button type="submit" class="btn btn-success px-4">Guardar</button>
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
	    $(document).ready(function () {
	        $('#formCultivo').submit(function (e) {
	            e.preventDefault();
	
	            // Verificación antes de enviar
	            const nuevaParcela = $('#nuevaParcela').val().trim();
	            const parcelaExistente = $('#parcelaExistente').val();
	
	            if (nuevaParcela === '' && parcelaExistente === '') {
	                $('#mensajeAjax').html('<div class="alert alert-danger">❌ Debes escribir una nueva parcela o seleccionar una existente.</div>');
	                return; // no sigue si no hay parcela válida
	            }
	
	            const formData = $(this).serialize();
	
	            $.post('${pageContext.request.contextPath}/cultivo/crear', formData)
	            .done(function() {
	                $('#mensajeAjax').html('<div class="alert alert-success"> Cultivo creado correctamente.</div>');
	                $('#formCultivo')[0].reset();
	
	                // Habilitamos ambos campos
	                $('#nuevaParcela').prop('disabled', false);
	                $('#parcelaExistente').prop('disabled', false);
	
	                // Recargar parcelas dinámicamente
	                $.get('${pageContext.request.contextPath}/cultivo/parcelas', function(parcelas) {
	                    const $select = $('#parcelaExistente');
	                    $select.empty(); // Limpiar opciones
	                    $select.append('<option value="">-- Seleccionar parcela existente --</option>');
	                    parcelas.forEach(function(parcela) {
	                        $select.append(`<option value="${parcela.parcelaId}">${parcela.nombre}</option>`);
	                    });
	                });
	            })
	
	        });
	
	        // Comportamiento mutuo exclusivo entre los campos de parcela
	        $('#nuevaParcela').on('input', function () {
	            $('#parcelaExistente').prop('disabled', $(this).val().trim().length > 0);
	        });
	
	        $('#parcelaExistente').on('change', function () {
	            $('#nuevaParcela').prop('disabled', $(this).val() !== '');
	        });
	    });
	</script>


</body>
</html>
