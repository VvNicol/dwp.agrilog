package dwp.agrilog.servicios;

import java.io.IOException;

/**
 * Interfaz para gestionar la recuperacion de contraseña.
 * 
 * @autor nrojlla 04032025
 */
public interface RecuperarContraseniaInterfaz {

	public void enviarCodigoAlCorreo(String correo) throws IOException;

	public void verificarCodigo(String correo, int codigo) throws IOException;

	public void cambiarContrasenia(String correo, String nuevaContrasenia) throws IOException;

}
