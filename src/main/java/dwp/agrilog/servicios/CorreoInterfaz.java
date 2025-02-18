package dwp.agrilog.servicios;

import jakarta.mail.MessagingException;

public interface CorreoInterfaz {
	
	public void enviarCorreo(String para, String asunto, String contenidoHtml) throws MessagingException;
	public void correoDeVerificacion(String correo, String token);
	public void correoRecuperacionConCodigo(String correo, int codigo);
}
