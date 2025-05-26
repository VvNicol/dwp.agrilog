/**
 * adminPanel.js
 */

function confirmarEliminacion(correo) {
	let confirmacion = new bootstrap.Modal(document
		.getElementById('modalConfirmacion'));
	document.getElementById('correoUsuario').textContent = correo;
	document.getElementById('inputCorreoConfirmacion').value = '';
	document.getElementById('mensajeErrorCorreo').style.display = 'none';

	document.getElementById('confirmarEliminar').onclick = function() {
		const correoEscrito = document
			.getElementById('inputCorreoConfirmacion').value.trim();

		if (correoEscrito === correo) {
			let form = document.createElement('form');
			form.method = 'POST';
			form.action = CONTEXTO + '/admin/eliminar-usuario';

			let input = document.createElement('input');
			input.type = 'hidden';
			input.name = 'correo';
			input.value = correo;

			form.appendChild(input);
			document.body.appendChild(form);
			form.submit();
		} else {
			document.getElementById('mensajeErrorCorreo').style.display = 'block';
		}
	};

	confirmacion.show();
}