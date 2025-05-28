package dwp.agrilog.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DTO que representa una notificación para transferencia entre la API y el DWP.
 * 
 * @author nrojlla
 */
public class NotificacionDto {

	private Long notificacionId;
	private CultivoDto cultivoId;
	private String mensaje;
	private int cantidad;
	private boolean estadoMensaje;
	private LocalDateTime fechaMensaje;
	private LocalDateTime fechaLectura;

	public String getFechaMensajeFormateada() {
		if (fechaMensaje == null)
			return "";
		return fechaMensaje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	}

	// Constructores

	public NotificacionDto() {
	}

	// Getters y Setters

	/**
	 * @return the fechaLectura
	 */
	public LocalDateTime getFechaLectura() {
		return fechaLectura;
	}

	/**
	 * @param fechaLectura the fechaLectura to set
	 */
	public void setFechaLectura(LocalDateTime fechaLectura) {
		this.fechaLectura = fechaLectura;
	}

	public Long getNotificacionId() {
		return notificacionId;
	}

	public void setNotificacionId(Long notificacionId) {
		this.notificacionId = notificacionId;
	}

	/**
	 * @return the estadoMensaje
	 */
	public boolean isEstadoMensaje() {
		return estadoMensaje;
	}

	/**
	 * @param estadoMensaje the estadoMensaje to set
	 */
	public void setEstadoMensaje(boolean estadoMensaje) {
		this.estadoMensaje = estadoMensaje;
	}

	/**
	 * @return the cultivoId
	 */
	public CultivoDto getCultivoId() {
		return cultivoId;
	}

	/**
	 * @param cultivoId the cultivoId to set
	 */
	public void setCultivoId(CultivoDto cultivoId) {
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

	/**
	 * @return the fechaMensaje
	 */
	public LocalDateTime getFechaMensaje() {
		return fechaMensaje;
	}

	/**
	 * @param fechaMensaje the fechaMensaje to set
	 */
	public void setFechaMensaje(LocalDateTime fechaMensaje) {
		this.fechaMensaje = fechaMensaje;
	}

}
