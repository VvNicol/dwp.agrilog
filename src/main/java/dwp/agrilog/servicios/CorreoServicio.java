package dwp.agrilog.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class CorreoServicio implements CorreoInterfaz {

	@Autowired
	private JavaMailSender correoRemitente;

	@Override
	public void enviarCorreo(String para, String asunto, String contenidoHtml) throws MessagingException {

		MimeMessage mensaje = correoRemitente.createMimeMessage();
		MimeMessageHelper asistente = new MimeMessageHelper(mensaje, true);

		asistente.setTo(para);
		asistente.setSubject(asunto);
		asistente.setText(contenidoHtml, true);

		correoRemitente.send(mensaje);
	}

	@Override
	public void correoDeVerificacion(String correo, String token) {

	    String asunto = "Verificación de correo :D";
	    
	    String contenido = "<h1>Verifica tu correo electrónico</h1>"
	            + "<p>Haz clic en el siguiente botón para verificar tu cuenta:</p>"
	            + "<a href=\"http://localhost:8081/proyectoAgricola/html/inicio/verificarCorreo.jsp?token=" + token + "\" "
	            + "style=\"display: inline-block; padding: 10px 20px; color: white; background-color: #007bff; "
	            + "text-decoration: none; border-radius: 5px;\">Verificar correo</a>"
	            + "<p>Si no reconoces esta acción, ignora este mensaje.</p>";

	    try {
	        enviarCorreo(correo, asunto, contenido);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
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
