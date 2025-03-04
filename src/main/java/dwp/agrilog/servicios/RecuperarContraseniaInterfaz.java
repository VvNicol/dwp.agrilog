package dwp.agrilog.servicios;

import java.io.IOException;

/**
 * Interfaz para gestionar la recuperación de contraseña.
 * 
 * @autor nrojlla 04032025
 */
public interface RecuperarContraseniaInterfaz {

	/**
	 * Envía un código de recuperación al correo del usuario.
	 * 
	 * @param correo Correo del usuario.
	 * @throws IOException Si hay un error en la operación.
	 */
	public void enviarCodigoAlCorreo(String correo) throws IOException;

	/**
	 * Verifica si el código ingresado por el usuario es válido.
	 * 
	 * @param correo Correo del usuario.
	 * @param codigo Código ingresado.
	 * @throws IOException Si hay un error en la operación.
	 */
	public void verificarCodigo(String correo, int codigo) throws IOException;

	/**
	 * Cambia la contraseña del usuario por una nueva.
	 * 
	 * @param correo           Correo del usuario.
	 * @param nuevaContrasenia Nueva contraseña.
	 * @throws IOException Si hay un error en la operación.
	 */
	public void cambiarContrasenia(String correo, String nuevaContrasenia) throws IOException;

}
