package br.com.mesquita.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.mesquita.model.Usuario;

public class AcessoUsuario implements UserDetails {
	private final Usuario usuario;

	public AcessoUsuario(Usuario user) {
		this.usuario = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(usuario.getRole()));
	}

	@Override
	public String getPassword() {
		return usuario.getSenha();
	}

	@Override
	public String getUsername() {
		return usuario.getUsername();
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
		return usuario.getAtivo();
	}
	
	public Long getId() {
		return usuario.getId();
	}
	
	public boolean isAdmin() {
		return this.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
	}
	
	public boolean isAtendente() {
		return this.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_ATENDENTE"));
	}
}
