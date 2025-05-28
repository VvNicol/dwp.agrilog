package dwp.agrilog.controladores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dwp.agrilog.dto.CultivoDto;
import dwp.agrilog.dto.ParcelaDto;
import dwp.agrilog.dto.UsuarioDTO;
import dwp.agrilog.seguridad.UsuarioAutenticado;
import dwp.agrilog.servicios.CultivoServicio;
import dwp.agrilog.servicios.ParcelaServicio;

/**
 * Controlador para gestionar lo relacionado con cultivos
 * 
 * @author nrojlla
 * @date 28/05/2025
 */

@Controller
@RequestMapping("/cultivo")
public class CultivoControlador {

	@Autowired
	private CultivoServicio cultivoServicio;

	@Autowired
	private ParcelaServicio parcelaServicio;

	/**
	 * Devuelve todas las parcelas asociadas al usuario autenticado.
	 *
	 * @param usuario Usuario autenticado
	 * @return Lista de parcelas en formato DTO
	 */
	@GetMapping("/parcelas")
	@ResponseBody
	public List<ParcelaDto> obtenerParcelasDelUsuario(@AuthenticationPrincipal UsuarioAutenticado usuario) {
		try {
			return parcelaServicio.obtenerParcelasPorUsuario(usuario.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Muestra el formulario de edición precargado con los datos del cultivo.
	 *
	 * @param id      ID del cultivo
	 * @param usuario Usuario autenticado
	 * @return Vista con el formulario de edición y datos cargados
	 */
	@GetMapping("/editar/{id}")
	public ModelAndView mostrarFormularioEdicion(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioAutenticado usuario) {
		ModelAndView model = new ModelAndView("usuario/usuarioFormCultivo");

		try {
			CultivoDto cultivo = cultivoServicio.buscarPorId(id);
			List<ParcelaDto> parcelas = parcelaServicio.obtenerParcelasPorUsuario(usuario.getId());

			model.addObject("cultivo", cultivo);
			model.addObject("parcelas", parcelas);
			model.addObject("modoEdicion", true); // flag para saber si es edición
		} catch (Exception e) {
			e.printStackTrace();
			model.addObject("error", "No se pudo cargar el cultivo.");
			model.setViewName("redirect:/usuario/panel");
		}

		return model;
	}

	/**
	 * Procesa los datos del formulario para actualizar un cultivo existente.
	 *
	 * @param id      ID del cultivo a actualizar
	 * @param params  Mapa con los datos del formulario
	 * @param usuario Usuario autenticado
	 * @return Respuesta HTTP con estado de la operación
	 */
	@PostMapping("/actualizar/{id}")
	@ResponseBody
	public ResponseEntity<?> actualizarCultivo(@PathVariable Long id, @RequestParam Map<String, String> params,
			@AuthenticationPrincipal UsuarioAutenticado usuario) {
		try {
			CultivoDto cultivo = cultivoServicio.buscarPorId(id);
			if (cultivo == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cultivo no encontrado.");
			}

			// Cargar datos desde el formulario
			cultivo.setNombre(params.get("nombrePlanta"));
			cultivo.setCantidad(Integer.parseInt(params.get("cantidad")));
			cultivo.setDescripcion(params.get("descripcion"));
			cultivo.setFechaSiembra(LocalDate.parse(params.get("fechaSiembra")));

			// Verificar parcela nueva o existente
			String nuevaParcela = params.get("nuevaParcela");
			String parcelaExistente = params.get("parcelaExistente");

			if ((nuevaParcela == null || nuevaParcela.isBlank())
					&& (parcelaExistente == null || parcelaExistente.isBlank())) {
				return ResponseEntity.badRequest()
						.body(" Debes escribir una nueva parcela o seleccionar una existente.");
			}

			// Crear nueva parcela si es necesario
			
			Long parcelaId;
			if (nuevaParcela != null && !nuevaParcela.isBlank()) {
				ParcelaDto parcelaDto = new ParcelaDto();
				parcelaDto.setNombre(nuevaParcela);
				UsuarioDTO usuarioDto = new UsuarioDTO();
				usuarioDto.setUsuarioId(usuario.getId());
				parcelaDto.setUsuarioId(usuarioDto);
				parcelaDto.setFechaRegistro(LocalDateTime.now());
				parcelaDto.setDescripcion("");
				parcelaId = parcelaServicio.CrearNuevaParcelaYObtenerId(parcelaDto);
			} else {
				parcelaId = Long.parseLong(parcelaExistente);
			}

			// Asociar parcela al cultivo y actualizar
			ParcelaDto parcelaRef = new ParcelaDto();
			parcelaRef.setParcelaId(parcelaId);
			cultivo.setParcelaId(parcelaRef);

			cultivoServicio.actualizarCultivo(cultivo);

			return ResponseEntity.ok(" Cultivo actualizado correctamente.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("❌ Ocurrió un problema al actualizar el cultivo.");
		}
	}

	/**
	 * Elimina un cultivo si el nombre ingresado coincide con el registrado.
	 *
	 * @param id                 ID del cultivo
	 * @param confirmacionNombre Nombre de la planta ingresado como confirmación
	 * @param redirect           Atributos para redireccionamiento
	 * @return Redirección al panel del usuario con mensaje de éxito o error
	 */
	@PostMapping("/eliminar/{id}")
	public String eliminarCultivo(@PathVariable Long id, @RequestParam String confirmacionNombre,
			RedirectAttributes redirect) {
		try {
			CultivoDto cultivo = cultivoServicio.buscarPorId(id);

			if (cultivo == null) {
				redirect.addFlashAttribute("error", "El cultivo no existe.");
				return "redirect:/usuario/panel";
			}

			if (!cultivo.getNombre().equalsIgnoreCase(confirmacionNombre.trim())) {
				redirect.addFlashAttribute("error", "El nombre no coincide.");
				return "redirect:/usuario/panel";
			}

			cultivoServicio.eliminarCultivo(id);
			redirect.addFlashAttribute("mensaje", "Cultivo eliminado con éxito.");
		} catch (Exception e) {
			e.printStackTrace();
			redirect.addFlashAttribute("error", "Error al eliminar el cultivo.");
		}
		return "redirect:/usuario/panel";
	}

	/**
	 * Muestra el formulario vacío para registrar un nuevo cultivo.
	 *
	 * @param usuario Usuario autenticado
	 * @return Vista con el formulario de creación y parcelas del usuario
	 */
	@GetMapping("/formulario")
	public ModelAndView mostrarFormulario(@AuthenticationPrincipal UsuarioAutenticado usuario) {
		ModelAndView modelAndView = new ModelAndView("usuario/usuarioFormCultivo");

		try {
			List<ParcelaDto> parcelas = parcelaServicio.obtenerParcelasPorUsuario(usuario.getId());
			modelAndView.addObject("parcelas", parcelas);
			modelAndView.addObject("usuario", usuario);
		} catch (Exception e) {
			modelAndView.addObject("error", "Error al cargar parcelas.");
		}

		return modelAndView;
	}

	/**
	 * Procesa el formulario para crear un cultivo y asociarlo a una parcela.
	 *
	 * @param params Datos enviados desde el formulario
	 * @param usuario Usuario autenticado
	 * @return Respuesta HTTP indicando éxito o error
	 */
	@PostMapping("/crear")
	@ResponseBody
	public ResponseEntity<?> crearCultivo(@RequestParam Map<String, String> params,
			@AuthenticationPrincipal UsuarioAutenticado usuario) {
		try {
			String nombrePlanta = params.get("nombrePlanta");
			int cantidad = Integer.parseInt(params.get("cantidad"));
			String descripcion = params.get("descripcion");
			LocalDate fechaSiembra = LocalDate.parse(params.get("fechaSiembra"));

			String nuevaParcela = params.get("nuevaParcela");
			String parcelaExistente = params.get("parcelaExistente");

			if ((nuevaParcela == null || nuevaParcela.isBlank())
					&& (parcelaExistente == null || parcelaExistente.isBlank())) {
				return ResponseEntity.badRequest()
						.body("Debes escribir una nueva parcela o seleccionar una existente.");
			}

			// Crear parcela si es nueva
			Long parcelaId;
			if (nuevaParcela != null && !nuevaParcela.isBlank()) {
				ParcelaDto parcelaDto = new ParcelaDto();
				parcelaDto.setNombre(nuevaParcela);
				UsuarioDTO usuarioDto = new UsuarioDTO();
				usuarioDto.setUsuarioId(usuario.getId());
				parcelaDto.setUsuarioId(usuarioDto);
				parcelaDto.setFechaRegistro(LocalDateTime.now());
				parcelaDto.setDescripcion("");
				parcelaId = parcelaServicio.CrearNuevaParcelaYObtenerId(parcelaDto);
			} else {
				parcelaId = Long.parseLong(parcelaExistente);
			}

			// Crear cultivo
			CultivoDto cultivoDto = new CultivoDto();
			cultivoDto.setUsuarioId(usuario.getId());
			cultivoDto.setNombre(nombrePlanta);
			cultivoDto.setCantidad(cantidad);
			cultivoDto.setDescripcion(descripcion);
			cultivoDto.setFechaSiembra(fechaSiembra);
			cultivoDto.setFechaRegistro(LocalDateTime.now());

			// ✅ Aquí el cambio importante
			ParcelaDto parcelaReferencia = new ParcelaDto();
			parcelaReferencia.setParcelaId(parcelaId);
			cultivoDto.setParcelaId(parcelaReferencia);

			cultivoServicio.crearNuevoCultivo(cultivoDto);

			return ResponseEntity.ok("Cultivo creado correctamente, puedes seguir creando nuevos cultivos.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocurrió un problema al crear el cultivo. Inténtalo de nuevo.");
		}
	}

}
