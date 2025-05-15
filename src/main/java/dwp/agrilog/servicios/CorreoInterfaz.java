package dwp.agrilog.servicios;

import jakarta.mail.MessagingException;

/**
 * Interfaz para el servicio de envío de correos electrónicos.
 * 
 * @autor nrojlla 25022025
 */
public interface CorreoInterfaz {

	/**
	 * Envía un correo electrónico con formato HTML.
	 *
	 * @param para          Destinatario del correo.
	 * @param asunto        Asunto del correo.
	 * @param contenidoHtml Contenido en formato HTML.
	 * @throws MessagingException Si ocurre un error en el envío del correo.
	 */
	public void enviarCorreo(String para, String asunto, String contenidoHtml) throws MessagingException;

	/**
	 * Envía un correo de verificación con un token.
	 *
	 * @param correo Correo del destinatario.
	 * @param token  Token de verificación.
	 * @throws MessagingException 
	 */
	public void correoDeVerificacion(String correo, String token) throws MessagingException;

	/**
	 * Envía un correo de recuperación de contraseña con un código de verificación.
	 *
	 * @param correo Correo del destinatario.
	 * @param codigo Código de verificación.
	 */
	public void correoRecuperacionConCodigo(String correo, int codigo);
}
