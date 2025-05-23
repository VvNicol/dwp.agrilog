package dwp.agrilog.controladores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dwp.agrilog.dto.CultivoDto;
import dwp.agrilog.dto.ParcelaDto;
import dwp.agrilog.dto.UsuarioDTO;
import dwp.agrilog.seguridad.UsuarioAutenticado;
import dwp.agrilog.servicios.CultivoServicio;
import dwp.agrilog.servicios.ParcelaServicio;

@Controller
@RequestMapping("/cultivo")
public class CultivoControlador {

    @Autowired
    private CultivoServicio cultivoServicio;

    @Autowired
    private ParcelaServicio parcelaServicio;
    
    /**
	 * Muestra la página de formulario de cultivo.
	 *
	 * @return Vista de la página de inicio.
	 */
    @GetMapping("/formulario")
    public ModelAndView mostrarFormulario(@AuthenticationPrincipal UsuarioAutenticado usuario) {
        ModelAndView modelAndView = new ModelAndView("usuario/usuarioFormCultivo");

        try {
            List<ParcelaDto> parcelas = parcelaServicio.obtenerParcelasPorUsuario(usuario.getId());
            modelAndView.addObject("parcelas", parcelas);
        } catch (Exception e) {
            modelAndView.addObject("error", "Error al cargar parcelas.");
        }

        return modelAndView;
    }


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

            if ((nuevaParcela == null || nuevaParcela.isBlank()) &&
                (parcelaExistente == null || parcelaExistente.isBlank())) {
                return ResponseEntity
                        .badRequest()
                        .body("❌ Debes escribir una nueva parcela o seleccionar una existente.");
            }

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

            return ResponseEntity.ok("✅ Cultivo creado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Ocurrió un problema al crear el cultivo. Inténtalo de nuevo.");
        }
    }


}
