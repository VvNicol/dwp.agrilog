package dwp.agrilog.dto;

import java.time.LocalDateTime;

/**
 * DTO que representa una parcela para la comunicación entre el DWP y la API.
 * 
 * @author nrojlla
 */
public class ParcelaDto {

	private Long parcelaId;
	private UsuarioDTO usuarioId;
	private String nombre;
	private String descripcion;
	private LocalDateTime fechaRegistro;

	// Constructores

	public ParcelaDto() {
	}

	public ParcelaDto(UsuarioDTO usuarioId, String nombre, String descripcion, LocalDateTime fechaRegistro) {
		this.usuarioId = usuarioId;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaRegistro = fechaRegistro;
	}

	public ParcelaDto(Long parcelaId, UsuarioDTO usuarioId, String nombre, String descripcion,
			LocalDateTime fechaRegistro) {
		this.parcelaId = parcelaId;
		this.usuarioId = usuarioId;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the parcelaId
	 */
	public Long getParcelaId() {
		return parcelaId;
	}

	/**
	 * @param parcelaId the parcelaId to set
	 */
	public void setParcelaId(Long parcelaId) {
		this.parcelaId = parcelaId;
	}

	/**
	 * @return the usuarioId
	 */
	public UsuarioDTO getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @param usuarioId the usuarioId to set
	 */
	public void setUsuarioId(UsuarioDTO usuarioId) {
		this.usuarioId = usuarioId;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the fechaRegistro
	 */
	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	// Getters y Setters

}
