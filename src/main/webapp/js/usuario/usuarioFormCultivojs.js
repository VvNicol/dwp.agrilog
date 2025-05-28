$(document).ready(function() {
	const form = $('#formCultivo');
	const mensaje = $('#mensajeAjax');
	const nuevaParcela = $('#nuevaParcela');
	const parcelaExistente = $('#parcelaExistente');

	const cultivoId = typeof CULTIVO_DATOS !== 'undefined' ? CULTIVO_DATOS.id : null;
	const esEdicion = typeof MODO_EDICION !== 'undefined' && MODO_EDICION === true;

	// Precargar datos en modo edición
	if (esEdicion && CULTIVO_DATOS) {		

		$('#nombrePlanta').val(CULTIVO_DATOS.nombre);
		$('#cantidad').val(CULTIVO_DATOS.cantidad);
		$('#descripcion').val(CULTIVO_DATOS.descripcion);
		$('#fechaSiembra').val(CULTIVO_DATOS.fechaSiembra);

		if (CULTIVO_DATOS.parcelaId) {
			$('#nuevaParcela').prop('disabled', true);
		} else {
			$('#nuevaParcela').prop('disabled', false);
		}
	}


	// Envío del formulario
	form.submit(function(e) {
		e.preventDefault();

		const valorNueva = nuevaParcela.val().trim();
		const valorExistente = parcelaExistente.val();

		if (valorNueva === '' && valorExistente === '') {
			mensaje.html('<div class="alert alert-danger">Debes escribir una nueva parcela o seleccionar una existente.</div>');
			setTimeout(() => mensaje.empty(), 10000);
			return;
		}

		capitalizarCampo("nombrePlanta");
		capitalizarCampo("descripcion");
		capitalizarCampo("nuevaParcela");

		const formData = form.serialize();
		const url = esEdicion
			? CONTEXTO + "/cultivo/actualizar/" + cultivoId
			: CONTEXTO + "/cultivo/crear";

		$.post(url, formData)
			.done(function(respuesta) {
				mensaje.html('<div class="alert alert-success">' + respuesta + '</div>');
				if (!esEdicion) {
					form[0].reset();
					nuevaParcela.prop('disabled', false);
					parcelaExistente.prop('disabled', false);
					// Recargar las parcelas desde el backend
					recargarParcelas();
				}
				setTimeout(() => mensaje.empty(), 10000);
			})
			.fail(function() {
				mensaje.html('<div class="alert alert-danger">Error al procesar el cultivo. Inténtalo de nuevo.</div>');
			});
	});

	// Alternancia entre nueva parcela y existente
	nuevaParcela.on('input', function() {
		parcelaExistente.prop('disabled', $(this).val().trim().length > 0);
	});

	parcelaExistente.on('change', function() {
		nuevaParcela.prop('disabled', $(this).val() !== '');
	});
});

// Capitaliza los campos (primera letra mayúscula, resto minúscula)
function capitalizarCampo(id) {
	const campo = document.getElementById(id);
	if (campo && campo.value.trim() !== "") {
		let texto = campo.value.trim();
		campo.value = texto.charAt(0).toUpperCase() + texto.slice(1).toLowerCase();
	}
}

// Recarga dinámica de parcelas al crear una nueva
function recargarParcelas() {
	$.get(CONTEXTO + "/parcela/parcelas", function(data) {
		const select = $('#parcelaExistente');
		select.empty();
		select.append('<option value="">-- Seleccionar parcela existente --</option>');

		data.forEach(parcela => {
			select.append(`<option value="${parcela.parcelaId}">${parcela.nombre}</option>`);
		});
	});
}

