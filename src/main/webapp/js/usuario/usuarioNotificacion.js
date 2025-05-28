document.addEventListener("DOMContentLoaded", function () {
	const btn = document.getElementById("btnNotificaciones");

	// Obtener y parsear notificaciones desde el atributo data
	const notificaciones = JSON.parse(btn.dataset.notificaciones || "[]");

	const popover = new bootstrap.Popover(btn, {
		trigger: 'click',
		html: true,
		placement: 'bottom',
		content: "<div class='popover-body'>Cargando notificaciones...</div>"
	});

	btn.addEventListener("click", function () {
		// Marcar automáticamente como leídas las nuevas
		notificaciones.forEach(noti => {
			if (!noti.estadoMensaje) {
				fetch("/notificacion/marcar-leida/" + noti.notificacionId, { method: 'POST' })
					.then(response => {
						if (response.ok) {
							console.log(`✅ Notificación ${noti.notificacionId} marcada como leída`);
							noti.estadoMensaje = true; // actualizar estado local para reflejarlo
						} else {
							console.warn(`⚠️ Fallo al marcar como leída la notificación ${noti.notificacionId}`);
						}
					})
					.catch(error => console.error("❌ Error al marcar como leída:", error));
			}
		});

		// Actualizar visualmente el botón
		btn.classList.remove("tiene-nuevas");

		// Mostrar las notificaciones en el popover
		let html = '';
		if (notificaciones.length === 0) {
			html = "<div class='text-center text-muted'>No tienes notificaciones por ver.</div>";
		} else {
			html += "<ul class='list-group small'>";
			notificaciones.forEach(n => {
				html += `
					<li class="list-group-item">
						${n.mensaje}
						${!n.estadoMensaje ? "<span class='badge bg-warning text-dark ms-2'>Nueva</span>" : ""}
					</li>
				`;
			});
			html += "</ul>";
		}

		popover.setContent({ '.popover-body': html });
	});

	// Mostrar punto rojo si hay alguna nueva
	if (notificaciones.some(n => !n.estadoMensaje)) {
		btn.classList.add("tiene-nuevas");
	}
});
