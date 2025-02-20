package dwp.agrilog.controladores;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

    @GetMapping("/panel")
    public ResponseEntity<Map<String, String>> mostrarPanelUsuario() {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Bienvenido al Panel de Usuario");
        return ResponseEntity.ok(respuesta);
    }
}
