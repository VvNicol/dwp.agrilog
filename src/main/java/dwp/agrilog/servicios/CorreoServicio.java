package dwp.agrilog.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Servicio para gestionar el envío de correos electrónicos.
 * Implementa la interfaz CorreoInterfaz.
 * 
 * @autor nrojlla 25022025
 */
@Service
public class CorreoServicio implements CorreoInterfaz {

	@Autowired
	private JavaMailSender correoRemitente;

	@Override
	public void enviarCorreo(String para, String asunto, String contenidoHtml) throws MessagingException {

		//1. Crear un mensaje de correo
		MimeMessage mensaje = correoRemitente.createMimeMessage(); 
		
		//2. Utilizar un asistente para configurar el mensaje
		MimeMessageHelper asistente = new MimeMessageHelper(mensaje, true);
		asistente.setTo(para);
		asistente.setSubject(asunto);
		asistente.setText(contenidoHtml, true);

		//3. Enviar mensaje
		correoRemitente.send(mensaje);
	}

	@Override
	public void correoDeVerificacion(String correo, String token) throws MessagingException {

		String asunto = "Verificación de correo :D";
	    String contenido = "<h1>Verifica tu correo electrónico</h1>"
	            + "<p>Haz clic en el siguiente botón para verificar tu cuenta:</p>"
	            + "<a href=\"https://agrilog.nicoldev.es/inicio/verificar-correo?token=" + token + "\" "
	            + "style=\"padding: 10px 20px; color: white; background-color: #007bff; text-decoration: none; border-radius: 5px; display: inline-block;\">"
	            + "Verificar correo</a>"
	            + "<p>Si no reconoces esta acción, ignora este mensaje.</p>";

	  
	        enviarCorreo(correo, asunto, contenido);
	   
	}

	@Override
	public void correoRecuperacionConCodigo(String correo, int codigo) {

		String asunto = "Recuperacion de contraseña";
		String contenido = "Hola, tu codigo de recuperacion es: " + codigo + "\n"
				+ "Este codigo expirara en 10 minutos";

		try {
			
			enviarCorreo(correo, asunto, contenido);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
