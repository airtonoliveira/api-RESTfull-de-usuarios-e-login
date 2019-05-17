package br.com.airton.repository;

import br.com.airton.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{


    List<Usuario> findAllByEmail(String email);

}
