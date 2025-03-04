package dwp.agrilog.servicios;

import java.io.IOException;

public interface RecuperarContraseniaInterfaz {

	public void enviarCodigoAlCorreo(String correo) throws IOException;

	public void verificarCodigo(String correo, int codigo) throws IOException;

	public void cambiarContrasenia(String correo, String nuevaContrasenia) throws IOException;

}
