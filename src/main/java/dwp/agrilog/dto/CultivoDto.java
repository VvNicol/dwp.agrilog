package dwp.agrilog.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO que representa un cultivo para transferencia entre la API y el DWP.
 * 
 * @author nrojlla
 */
public class CultivoDto {

	private Long cultivoId;
	private Long parcelaId;
	private String nombre;
	private String descripcion;
	private Integer cantidad;
	private LocalDate fechaSiembra;
	private LocalDateTime fechaRegistro;

	// Constructores

	public CultivoDto() {
	}

	public CultivoDto(Long parcelaId, String nombre, String descripcion, Integer cantidad,
			LocalDate fechaSiembra, LocalDateTime fechaRegistro) {
		this.parcelaId = parcelaId;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.fechaSiembra = fechaSiembra;
		this.fechaRegistro = fechaRegistro;
	}

	public CultivoDto(Long cultivoId, Long parcelaId, String nombre, String descripcion, Integer cantidad,
			LocalDate fechaSiembra, LocalDateTime fechaRegistro) {
		this.cultivoId = cultivoId;
		this.parcelaId = parcelaId;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.fechaSiembra = fechaSiembra;
		this.fechaRegistro = fechaRegistro;
	}

	// Getters y Setters

	public Long getCultivoId() {
		return cultivoId;
	}

	public void setCultivoId(Long cultivoId) {
		this.cultivoId = cultivoId;
	}

	public Long getParcelaId() {
		return parcelaId;
	}

	public void setParcelaId(Long parcelaId) {
		this.parcelaId = parcelaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public LocalDate getFechaSiembra() {
		return fechaSiembra;
	}

	public void setFechaSiembra(LocalDate fechaSiembra) {
		this.fechaSiembra = fechaSiembra;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
}
