package dwp.agrilog.servicios;

import java.util.List;
import dwp.agrilog.dto.ParcelaDto;

/**
 * Interfaz para operaciones sobre parcelas.
 * 
 * @autor nrojlla
 * @fecha 27/05/2025
 */
public interface ParcelaInterfaz {

	/**
	 * 1. Obtiene todas las parcelas asociadas a un usuario.
	 * 
	 * @param usuarioId ID del usuario
	 * @return Lista de parcelas
	 */
	public List<ParcelaDto> obtenerParcelasPorUsuario(Long usuarioId) ;

	/**
	 * 2. Crea una nueva parcela sin retornar su ID.
	 * 
	 * @param parcelaDto Datos de la nueva parcela
	 * @throws Exception si ocurre un error en la creación
	 */
	public void CrearNuevaParcela(ParcelaDto parcelaDto) throws Exception;

	/**
	 * 3. Crea una nueva parcela y devuelve su ID generado.
	 * 
	 * @param parcelaDto Datos de la nueva parcela
	 * @return ID de la parcela creada
	 * @throws Exception si ocurre un error en la creación
	 */
	public Long CrearNuevaParcelaYObtenerId(ParcelaDto parcelaDto) throws Exception;
}
