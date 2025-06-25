package com.eventorganizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventorganizer.entity.Usuario;
import com.eventorganizer.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired // Injeta automaticamente o UsuarioRepository para acessar métodos de banco
               // (salvar, buscar, deletar)
    private UsuarioRepository usuarioRepository;

    // Salva o usuário no banco e executa a operação imediatamente (flush)
    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.saveAndFlush(usuario);
    }

    // Busca um usuário pelo email. Lança exceção se não encontrar.
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não encontrado"));
    }

    // deleta um usuario pelo e-mail!
    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public Usuario atualizarUsuario(Integer id, Usuario dadosAtualizados) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + id + " não encontrado"));

        usuarioExistente.setPrimeiroNome(dadosAtualizados.getPrimeiroNome());
        usuarioExistente.setSobrenome(dadosAtualizados.getSobrenome());
        usuarioExistente.setIdade(dadosAtualizados.getIdade());
        usuarioExistente.setEmail(dadosAtualizados.getEmail());
        usuarioExistente.setSenha(dadosAtualizados.getSenha());

        return usuarioRepository.save(usuarioExistente);
    }
}
