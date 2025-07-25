package com.eventorganizer.Controller;

import com.eventorganizer.entity.Entrada;
import com.eventorganizer.entity.Espaco;
import com.eventorganizer.entity.Evento;
import com.eventorganizer.entity.Periodicidade;
import com.eventorganizer.entity.Usuario;
import com.eventorganizer.service.EventoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/evento")
public class EventoViewController {

    @Autowired
    private EventoService eventoService;

    // Exibe a tela inicial (depois do login)
    @GetMapping("/pagina")
    public String paginaEventos() {
        return "telaInicial";
    }

    @GetMapping("/telaInicial")
    public String mostrarTelaInicial() {
        return "telaInicial";
    }

    // Exibe o formulário de cadastro
    @GetMapping("/cadastrar")
    public String exibirFormulario() {
        return "cadastrarEvento";
    }

    // Processa o formulário enviado
    @PostMapping("/cadastrar")
    public String processarCadastro(
            @RequestParam String nomeEvento,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) String local,
            @RequestParam String dataHora,
            @RequestParam(required = false) String faixaEtaria,
            @RequestParam String periodicidade,
            @RequestParam String espaco,
            @RequestParam(required = false, defaultValue = "0") int capacidade,
            @RequestParam String entrada,
            @AuthenticationPrincipal Usuario usuarioLogado  // pega o usuário logado via Spring Security
    ) {
        if (usuarioLogado == null) {
            System.out.println("Usuário não está logado no contexto de segurança!");
            return "redirect:/login";
        }

        LocalDateTime dataHoraConvertida = LocalDateTime.parse(dataHora, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Evento evento = new Evento(
                nomeEvento,
                descricao,
                local,
                dataHoraConvertida,
                faixaEtaria,
                Periodicidade.valueOf(periodicidade),
                Espaco.valueOf(espaco),
                capacidade,
                Entrada.valueOf(entrada),
                usuarioLogado
        );

        eventoService.salvarEvent(evento);

        return "redirect:/evento/telaInicial";
    }
}
