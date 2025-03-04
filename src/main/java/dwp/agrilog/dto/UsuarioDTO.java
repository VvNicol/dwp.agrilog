package dwp.agrilog.dto;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para representar los datos de un usuario.
 * 
 * @autor nrojlla 25022025
 */
public class UsuarioDTO {

	private Long usuarioId; // Identificador único del usuario

	private String nombreCompleto; // Nombre completo del usuario

	private String telefono; // Número de teléfono del usuario

	private String correo; // Correo electrónico del usuario

	private String rol; // Rol del usuario (ej. ADMIN, USUARIO)

	private String contrasenia; // Contraseña encriptada del usuario

	private String token; // Token para autenticación o verificación

	private byte[] imagen; // Imagen de perfil del usuario en formato binario

	private LocalDateTime fechaRegistro; // Fecha y hora en que se registró el usuario

	private Boolean autenticacionExterna = false; // Indica si el usuario usa autenticación externa

	private boolean correoValidado = false; // Indica si el correo del usuario ha sido validado

	private String proveedor; // Proveedor de autenticación externa (ej. Google, Facebook)

	private String externoId; // ID del usuario en la plataforma externa

	private LocalDateTime tokenExpiracionFecha; // Fecha de expiración del token de autenticación
	
	private int codigoRecuperacion = 0; //Codigo de recuperacion 
	
	private LocalDateTime codigoExpiracionFecha; // Fecha de expiracion del codigo

	/**
	 * Constructor vacío.
	 */
	public UsuarioDTO() {
		super();
	}

	/**
	 * Constructor con parámetros básicos para inicio de sesión.
	 * 
	 * @param correo      Correo del usuario.
	 * @param contrasenia Contraseña del usuario.
	 */
	public UsuarioDTO(String correo, String contrasenia) {
		super();
		this.correo = correo;
		this.contrasenia = contrasenia;
	}

	// Métodos Getter y Setter
	
	/**
	 * @return the usuarioId
	 */
	public Long getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @param usuarioId the usuarioId to set
	 */
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * @param nombreCompleto the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * @return the contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * @param contrasenia the contrasenia to set
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the imagen
	 */
	public byte[] getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
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

	/**
	 * @return the autenticacionExterna
	 */
	public Boolean getAutenticacionExterna() {
		return autenticacionExterna;
	}

	/**
	 * @param autenticacionExterna the autenticacionExterna to set
	 */
	public void setAutenticacionExterna(Boolean autenticacionExterna) {
		this.autenticacionExterna = autenticacionExterna;
	}

	/**
	 * @return the correoValidado
	 */
	public boolean isCorreoValidado() {
		return correoValidado;
	}

	/**
	 * @param correoValidado the correoValidado to set
	 */
	public void setCorreoValidado(boolean correoValidado) {
		this.correoValidado = correoValidado;
	}

	/**
	 * @return the proveedor
	 */
	public String getProveedor() {
		return proveedor;
	}

	/**
	 * @param proveedor the proveedor to set
	 */
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	/**
	 * @return the externoId
	 */
	public String getExternoId() {
		return externoId;
	}

	/**
	 * @param externoId the externoId to set
	 */
	public void setExternoId(String externoId) {
		this.externoId = externoId;
	}

	public LocalDateTime getTokenExpiracionFecha() {
		return tokenExpiracionFecha;
	}

	/**
	 * @return the codigoRecuperacion
	 */
	public int getCodigoRecuperacion() {
		return codigoRecuperacion;
	}

	/**
	 * @param codigoRecuperacion the codigoRecuperacion to set
	 */
	public void setCodigoRecuperacion(int codigoRecuperacion) {
		this.codigoRecuperacion = codigoRecuperacion;
	}

	public void setTokenExpiracionFecha(LocalDateTime tokenExpiracionFecha) {
		this.tokenExpiracionFecha = tokenExpiracionFecha;
	}

	/**
	 * @return the codigoExpiracionFecha
	 */
	public LocalDateTime getCodigoExpiracionFecha() {
		return codigoExpiracionFecha;
	}

	/**
	 * @param codigoExpiracionFecha the codigoExpiracionFecha to set
	 */
	public void setCodigoExpiracionFecha(LocalDateTime codigoExpiracionFecha) {
		this.codigoExpiracionFecha = codigoExpiracionFecha;
	}

}
