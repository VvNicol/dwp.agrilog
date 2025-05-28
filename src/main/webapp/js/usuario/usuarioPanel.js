//usuarioPanel.js
function mostrarDescripcionCompleta(id, descripcion) {
	document.getElementById('desc-' + id).innerText = descripcion;
}

//MODAL
function limpiarEspacios(formulario) {
	const input = formulario.querySelector('input[name="confirmacionNombre"]');
	if (input) {
		input.value = input.value.trim(); // Elimina espacios al inicio y final
	}
	return true; // Continúa con el envío
}
