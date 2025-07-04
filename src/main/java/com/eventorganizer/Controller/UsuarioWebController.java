package com.eventorganizer.Controller;

import com.eventorganizer.entity.Usuario;
import com.eventorganizer.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioWebController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario/alterarPerfil")
public String mostrarAlterarPerfil(Model model, HttpSession session) {
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    if (usuarioLogado == null) {
        return "redirect:/login";
    }
    model.addAttribute("usuario", usuarioLogado);
    return "alterarUsuario";  
}

    @GetMapping("/editarPerfil")
    public String editarPerfil(Model model, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuarioLogado);
        return "editarPerfil";
    }

    @PostMapping("/atualizarUsuario")
    public String atualizarUsuario(@ModelAttribute Usuario usuarioForm, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        usuarioLogado.setPrimeiroNome(usuarioForm.getPrimeiroNome());
        usuarioLogado.setSobrenome(usuarioForm.getSobrenome());
        usuarioLogado.setIdade(usuarioForm.getIdade());
        usuarioLogado.setEmail(usuarioForm.getEmail());

        if (usuarioForm.getSenha() != null && !usuarioForm.getSenha().isEmpty()) {
            usuarioLogado.setSenha(usuarioForm.getSenha());
        }

        usuarioService.salvarUsuario(usuarioLogado);

        return "redirect:/login";
    }
}

