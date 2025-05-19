package dwp.agrilog.dto;

import java.time.LocalDateTime;

public class TokenDto {

	private Long usuarioId;
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
