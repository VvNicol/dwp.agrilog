package dwp.agrilog.dto;

import java.time.LocalDateTime;

/**
 * DTO que representa un token de verificación para el usuario
 * 
 * @author nrojlla
 * @date 28/05/2025
 */
public class TokenDto {

	private UsuarioDTO usuario;
	private Long tokenId;
	private String token;
	private LocalDateTime tokenExpiracionFecha;

	;

	/**
	 * Constructor vacio
	 */
	public TokenDto() {
		super();
	}

	

	/**
	 * @return the usuario
	 */
	public UsuarioDTO getUsuario() {
		return usuario;
	}



	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}



	/**
	 * @return the tokenId
	 */
	public Long getTokenId() {
		return tokenId;
	}

	/**
	 * @param tokenId the tokenId to set
	 */
	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
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
	 * @return the tokenExpiracionFecha
	 */
	public LocalDateTime getTokenExpiracionFecha() {
		return tokenExpiracionFecha;
	}

	/**
	 * @param tokenExpiracionFecha the tokenExpiracionFecha to set
	 */
	public void setTokenExpiracionFecha(LocalDateTime tokenExpiracionFecha) {
		this.tokenExpiracionFecha = tokenExpiracionFecha;
	}

}
