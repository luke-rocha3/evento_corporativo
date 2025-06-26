package com.eventorganizer.Controller;

import com.eventorganizer.entity.Usuario;
import com.eventorganizer.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    // Exibe a página de login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // arquivo login.html no templates
    }

    // Processa o login
   @PostMapping("/login")
public String processarLogin(@RequestParam("username") String email,
                            @RequestParam("password") String senha,
                            Model model,
                            HttpSession session) {
    try {
        Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);

        // Ideal: aqui você deveria usar hash para comparar senha em produção
        if (usuario.getSenha().equals(senha)) {
            session.setAttribute("usuarioLogado", usuario);  // grava o usuário na sessão
            return "redirect:/evento/telaInicial";          // redireciona após login
        } else {
            model.addAttribute("erro", "Senha incorreta!");
            return "login";                                 // volta para login com erro
        }
    } catch (RuntimeException e) {
        model.addAttribute("erro", "Email não encontrado!");
        return "login";                                     // volta para login com erro
    }
}


    // Exibe página de cadastro de usuário
    @GetMapping("/cadastrarUsuario")
    public String mostrarCadastro() {
        return "cadastrarUsuario"; // arquivo cadastrarUsuario.html no templates
    }

   @PostMapping("/cadastrarUsuario")
public String processarCadastro(@RequestParam("primeiroNome") String primeiroNome,
                               @RequestParam("sobrenome") String sobrenome,
                               @RequestParam("idade") int idade,
                               @RequestParam("email") String email,
                               @RequestParam("senha") String senha,
                               Model model) {

    Usuario usuario = new Usuario();
    usuario.setPrimeiroNome(primeiroNome);
    usuario.setSobrenome(sobrenome);
    usuario.setIdade(idade);
    usuario.setEmail(email);
    usuario.setSenha(senha); 

    usuarioService.salvarUsuario(usuario);

    return "redirect:/login";
}

}
