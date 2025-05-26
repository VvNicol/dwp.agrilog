/**
 * usuarioFormCultivojs.js
 */
// Esperar que el DOM cargue completamente
$(document).ready(function() {
	const form = $('#formCultivo');
	const mensaje = $('#mensajeAjax');
	const nuevaParcela = $('#nuevaParcela');
	const parcelaExistente = $('#parcelaExistente');

	// Manejo del envío del formulario
	form.submit(function(e) {
		e.preventDefault();

		// Validar si hay una parcela válida
		const valorNueva = nuevaParcela.val().trim();
		const valorExistente = parcelaExistente.val();

		if (valorNueva === '' && valorExistente === '') {
			mensaje.html('<div class="alert alert-danger"> Debes escribir una nueva parcela o seleccionar una existente.</div>');
			setTimeout(() => mensaje.empty(), 10000);
			return;
		}

		// Capitalizar valores antes de enviar
		capitalizarCampo("nombrePlanta");
		capitalizarCampo("descripcion");
		capitalizarCampo("nuevaParcela");

		const formData = form.serialize();

		$.post(CONTEXTO + "/cultivo/crear", formData)
			.done(function() {
				mensaje.html('<div class="alert alert-success"> Cultivo creado correctamente, puedes seguir creando más cultivos.</div>');
				form[0].reset();
				nuevaParcela.prop('disabled', false);
				parcelaExistente.prop('disabled', false);
				setTimeout(() => mensaje.empty(), 10000);

				// Recargar parcelas
				$.get(CONTEXTO + "/cultivo/parcelas", function(parcelas) {
					parcelaExistente.empty();
					parcelaExistente.append('<option value="">-- Seleccionar parcela existente --</option>');
					parcelas.forEach(function(parcela) {
						parcelaExistente.append(`<option value="${parcela.parcelaId}">${parcela.nombre}</option>`);
					});
				});
			});
	});

	// Excluir selección simultánea
	nuevaParcela.on('input', function() {
		parcelaExistente.prop('disabled', $(this).val().trim().length > 0);
	});

	parcelaExistente.on('change', function() {
		nuevaParcela.prop('disabled', $(this).val() !== '');
	});
});

// Capitaliza y limpia espacios de un campo
function capitalizarCampo(id) {
	const campo = document.getElementById(id);
	if (campo && campo.value.trim() !== "") {
		let texto = campo.value.trim();
		campo.value = texto.charAt(0).toUpperCase() + texto.slice(1).toLowerCase();
	}
}


