package br.com.airton.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.airton.model.Usuario;
import br.com.airton.repository.UsuarioRepository;
import br.com.airton.response.TokenResponse;

@Component
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public TokenResponse getUsuarioResponseForSucessfullSignin(String token) {		    
	    return new TokenResponse(token);
	}

	public Usuario updateUserLastLogin(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);	
		usuario.setLast_login((new Date()));
	    usuarioRepository.save(usuario);
		return usuario;
	}
	
	
}
