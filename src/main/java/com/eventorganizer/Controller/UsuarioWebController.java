package com.eventorganizer.Controller;

import com.eventorganizer.entity.Usuario;
import com.eventorganizer.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioWebController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario/alterarPerfil")
    public String mostrarAlterarPerfil(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuarioLogado);
        return "alterarUsuario";
    }

    @GetMapping("/editarPerfil")
    public String editarPerfil(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuarioLogado);
        return "editarPerfil";
    }

    @PostMapping("/atualizarUsuario")
    public String atualizarUsuario(@ModelAttribute Usuario usuarioForm, 
                                   @AuthenticationPrincipal Usuario usuarioLogado,
                                   HttpSession session) {
        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        // Atualiza os dados do usuário logado
        usuarioLogado.setPrimeiroNome(usuarioForm.getPrimeiroNome());
        usuarioLogado.setSobrenome(usuarioForm.getSobrenome());
        usuarioLogado.setIdade(usuarioForm.getIdade());
        usuarioLogado.setEmail(usuarioForm.getEmail());

        if (usuarioForm.getSenha() != null && !usuarioForm.getSenha().isEmpty()) {
            usuarioLogado.setSenha(usuarioForm.getSenha());
        }

        // Salva no banco
        usuarioService.salvarUsuario(usuarioLogado);

        // Atualiza o contexto de segurança com o usuário atualizado (opcional, pode ser removido se for invalidar sessão)
        atualizarAutenticacao(usuarioLogado);

        // Invalida a sessão para forçar logout e redireciona para login
        session.invalidate();

        return "redirect:/login";
    }

    private void atualizarAutenticacao(Usuario usuarioAtualizado) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                usuarioAtualizado,
                usuarioAtualizado.getPassword(),
                usuarioAtualizado.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
