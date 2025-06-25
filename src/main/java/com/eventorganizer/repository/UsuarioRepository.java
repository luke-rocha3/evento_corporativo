package com.eventorganizer.repository;

import com.eventorganizer.entity.Usuario;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository 
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    Optional<Usuario> findByEmail(String email);
    // optional -> criar uma exceção/alternativa,caso o email não exista!

    @Transactional // se der algum erro,ele não pode deletar esse email!+
    void deleteByEmail(String email);
}
