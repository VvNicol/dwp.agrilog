package dwp.agrilog.servicios;

import jakarta.servlet.http.HttpSession;

/**
 * Interfaz para el servicio de gestión de registros de sesión y eventos de usuarios.
 * Define el método para registrar eventos en archivos de sesión.
 * 
 * @autor nrojlla 04032025
 */
public interface FicheroInterfaz {
	
	/**
     * Registra eventos en archivos de sesión para usuarios autenticados y anónimos.
     * 
     * @param session       Sesión del usuario actual.
     * @param descripcion   Descripción del evento (Ejemplo: "Usuario inició sesión").
     * @param tipoRegistro  Tipo de registro (Ejemplo: "Página visitada").
     */
	public void registrarEvento(HttpSession session, String descripcion, String tipoRegistro) ;

}
