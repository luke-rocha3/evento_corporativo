package com.eventorganizer.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventorganizer.entity.Usuario;
import com.eventorganizer.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Evita recriptografar senhas já codificadas
    public Usuario salvarUsuario(Usuario usuario) {
        if (usuario.getSenha() != null && !usuario.getSenha().startsWith("$2a$")) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        return usuarioRepository.saveAndFlush(usuario);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não encontrado"));
    }

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

        // ✅ Se a senha for nova, criptografa — mas evita duplicar
        if (dadosAtualizados.getSenha() != null && !dadosAtualizados.getSenha().isEmpty()) {
            if (!dadosAtualizados.getSenha().startsWith("$2a$")) {
                usuarioExistente.setSenha(passwordEncoder.encode(dadosAtualizados.getSenha()));
            } else {
                usuarioExistente.setSenha(dadosAtualizados.getSenha());
            }
        }

        return usuarioRepository.save(usuarioExistente);
    }
}
