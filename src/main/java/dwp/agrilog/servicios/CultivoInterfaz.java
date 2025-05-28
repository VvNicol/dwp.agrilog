package dwp.agrilog.servicios;

import java.util.List;

import dwp.agrilog.dto.CultivoDto;

/**
 * Interfaz de servicio para operaciones CRUD sobre cultivos.
 * 
 * @author nrojlla
 * @date 28/05/2025
 */
public interface CultivoInterfaz {

	/**
	 * 1. Crea un nuevo cultivo a partir de los datos proporcionados.
	 * 
	 * @param cultivoDto Datos del cultivo a crear
	 * @throws Exception si ocurre un error durante la creación
	 */
	public void crearNuevoCultivo(CultivoDto cultivoDto) throws Exception;

	/**
	 * 2. Obtiene todos los cultivos registrados por un usuario.
	 * 
	 * @param usuarioId ID del usuario
	 * @return Lista de cultivos del usuario
	 */
	public List<CultivoDto> obtenerCultivosPorUsuario(Long usuarioId);

	/**
	 * 3. Elimina un cultivo por su ID.
	 * 
	 * @param id ID del cultivo
	 * @throws Exception si ocurre un error durante la eliminación
	 */
	public void eliminarCultivo(Long id) throws Exception;

	/**
	 * 4. Busca un cultivo por su ID.
	 * 
	 * @param id ID del cultivo
	 * @return Cultivo encontrado o null si no existe
	 */
	public CultivoDto buscarPorId(Long id);

	/**
	 * 5. Actualiza los datos de un cultivo existente.
	 * 
	 * @param cultivo Cultivo actualizado
	 * @throws Exception si ocurre un error durante la actualización
	 */
	public void actualizarCultivo(CultivoDto cultivo) throws Exception;
}
