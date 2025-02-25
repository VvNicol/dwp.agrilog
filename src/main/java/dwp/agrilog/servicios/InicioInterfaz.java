package dwp.agrilog.servicios;

import java.util.Map;

import dwp.agrilog.dto.UsuarioDTO;

public interface InicioInterfaz {

	public Map<String, String> iniciarSesionUsuario(UsuarioDTO usuario) throws Exception;
	
	public boolean verificarCorreo(String token) throws Exception;
}
