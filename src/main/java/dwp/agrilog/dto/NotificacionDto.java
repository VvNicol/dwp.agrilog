package dwp.agrilog.dto;

import java.time.LocalDate;

/**
 * DTO que representa una notificación para transferencia entre la API y el DWP.
 * 
 * @author nrojlla
 */
public class NotificacionDto {

	private Long notificacionId;
	private Long cultivoId;
	private String mensaje;
	private int cantidad;
	private LocalDate fechaMensaje;

	// Constructores

	public NotificacionDto() {
	}

	public NotificacionDto(Long cultivoId, String mensaje, int cantidad, LocalDate fechaMensaje) {
		this.cultivoId = cultivoId;
		this.mensaje = mensaje;
		this.cantidad = cantidad;
		this.fechaMensaje = fechaMensaje;
	}

	public NotificacionDto(Long notificacionId, Long cultivoId, String mensaje, int cantidad, LocalDate fechaMensaje) {
		this.notificacionId = notificacionId;
		this.cultivoId = cultivoId;
		this.mensaje = mensaje;
		this.cantidad = cantidad;
		this.fechaMensaje = fechaMensaje;
	}

	// Getters y Setters

	public Long getNotificacionId() {
		return notificacionId;
	}

	public void setNotificacionId(Long notificacionId) {
		this.notificacionId = notificacionId;
	}

	public Long getCultivoId() {
		return cultivoId;
	}

	public void setCultivoId(Long cultivoId) {
		this.cultivoId = cultivoId;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public LocalDate getFechaMensaje() {
		return fechaMensaje;
	}

	public void setFechaMensaje(LocalDate fechaMensaje) {
		this.fechaMensaje = fechaMensaje;
	}
}
