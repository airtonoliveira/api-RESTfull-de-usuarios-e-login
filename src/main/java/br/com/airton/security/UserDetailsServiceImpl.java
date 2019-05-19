package br.com.airton.security;

import br.com.airton.Util.UserSS;
import br.com.airton.model.Usuario;
import br.com.airton.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository usuariopRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = (Usuario) usuariopRepository.findByEmail(email);

        if(usuario==null){
            return null;
        }

        return new UserSS(usuario.getEmail(), usuario.getPassword());
    }
}
