package dwp.agrilog.controladores;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dwp.agrilog.dto.ParcelaDto;
import dwp.agrilog.seguridad.UsuarioAutenticado;
import dwp.agrilog.servicios.ParcelaServicio;

@RestController
@RequestMapping("/parcela")
public class ParcelaControlador {

	@Autowired
	private ParcelaServicio parcelaServicio;

	@PostMapping("/crear")
	public ResponseEntity<Map<String, String>> crearGuardar(@ModelAttribute ParcelaDto parcelaDto,
	                                                        @AuthenticationPrincipal UsuarioAutenticado usuario) {
	    Map<String, String> respuesta = new HashMap<>();

	    System.out.println("Usuario autenticado: " + usuario);
	    System.out.println("ID autenticado: " + usuario.getId());

	    
	    try {
	    	parcelaDto.setUsuarioId(usuario.getId());
	        parcelaDto.setFechaRegistro(LocalDateTime.now());

	        if (parcelaDto.getDescripcion() == null) {
	            parcelaDto.setDescripcion("");
	        }
	        parcelaServicio.CrearNuevaParcela(parcelaDto);

	        respuesta.put("mensaje", "Parcela creada y asociada correctamente.");
	        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);

	    } catch (Exception e) {
	        e.printStackTrace();
	        respuesta.put("error", "Error al crear la parcela: " + e.getMessage());
	        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
	    }
	}


}
