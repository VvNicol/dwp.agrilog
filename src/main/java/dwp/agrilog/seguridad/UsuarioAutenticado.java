package dwp.agrilog.seguridad;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * Clase que implementa UserDetails para representar al usuario autenticado en
 * Spring Security. Utilizada para acceder a los datos del usuario actual en el
 * contexto de seguridad.
 * 
 * @author nrojlla
 * @date 28/05/2025
 */
public class UsuarioAutenticado implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final Long id;
	private final String correo;
	private final String rol;

	public UsuarioAutenticado(Long id, String correo, String rol) {
		this.id = id;
		this.correo = correo;
		this.rol = rol;
	}

	public Long getId() {
		return id;
	}

	public String getRol() {
		return rol;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(rol));
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return correo;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
