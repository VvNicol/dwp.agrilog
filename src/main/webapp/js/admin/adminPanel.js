/**
 * adminPanel.js
 */
document.addEventListener("DOMContentLoaded", function () {
	const inputBusqueda = document.getElementById("busquedaCorreo");
	const chkAdmin = document.getElementById("filtroAdmin");
	const chkUsuario = document.getElementById("filtroUsuario");
	const filas = document.querySelectorAll("#tablaUsuarios tr");

	function aplicarFiltros() {
		const texto = inputBusqueda.value.trim().toLowerCase();
		const mostrarAdmin = chkAdmin.checked;
		const mostrarUsuario = chkUsuario.checked;

		filas.forEach(fila => {
			let correo = "";
			let rol = "";

			const tdCorreo = fila.querySelector(".td-correo");
			const tdRol = fila.querySelector(".td-rol");

			if (tdCorreo) correo = tdCorreo.textContent.toLowerCase();
			if (tdRol) rol = tdRol.textContent.toUpperCase();

			const coincideCorreo = correo.includes(texto);
			const coincideRol =
				(mostrarAdmin && rol === "ADMIN") ||
				(mostrarUsuario && rol === "USUARIO");

			fila.style.display = (coincideCorreo && coincideRol) ? "" : "none";
		});
	}

	// Escuchas de eventos
	inputBusqueda.addEventListener("input", aplicarFiltros);
	chkAdmin.addEventListener("change", aplicarFiltros);
	chkUsuario.addEventListener("change", aplicarFiltros);

	// Verificar existencia de logs
	filas.forEach(fila => {
		const tdCorreo = fila.querySelector(".td-correo");
		if (tdCorreo) {
			const correo = tdCorreo.textContent.trim();
			const btn = document.getElementById("btnLog_" + correo);
			
			if (btn) {
				fetch(CONTEXTO + "/admin/existe-log?correo=" + encodeURIComponent(correo))
					.then(res => res.json())
					.then(existe => {
						if (!existe) {
							btn.classList.add("disabled");
							btn.textContent = "Sin log";
							btn.removeAttribute("href");
							btn.removeAttribute("download");
							btn.setAttribute("title", "El usuario aún no ha generado un log.");
						}
					})
					.catch(err => {
						console.error("Error al verificar existencia del log:", err);
					});
			}
		}
	});
});

function confirmarEliminacion(correo) {
	let confirmacion = new bootstrap.Modal(document.getElementById('modalConfirmacion'));
	document.getElementById('correoUsuario').textContent = correo;
	document.getElementById('inputCorreoConfirmacion').value = '';
	document.getElementById('mensajeErrorCorreo').style.display = 'none';

	document.getElementById('confirmarEliminar').onclick = function () {
		const correoEscrito = document.getElementById('inputCorreoConfirmacion').value.trim();

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
