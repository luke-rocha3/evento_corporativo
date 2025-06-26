package com.eventorganizer.Controller;

import com.eventorganizer.entity.Entrada;
import com.eventorganizer.entity.Espaco;
import com.eventorganizer.entity.Evento;
import com.eventorganizer.entity.Periodicidade;
import com.eventorganizer.entity.Usuario;
import com.eventorganizer.service.EventoService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
            HttpSession session
    ) {
        // Recupera o usuário logado
        Usuario dono = (Usuario) session.getAttribute("usuarioLogado");

        if (dono == null) {
                    System.out.println("Usuário não está logado na sessão!");
            return "redirect:/login";
        }
            System.out.println("Usuário logado: " + dono.getPrimeiroNome() + " " + dono.getSobrenome());

        
         System.out.println("nomeEvento: " + nomeEvento);
        System.out.println("dataHora: " + dataHora);
        System.out.println("periodicidade: " + periodicidade);
        System.out.println("espaco: " + espaco);
        System.out.println("entrada: " + entrada);


        // Converte dataHora para LocalDateTime
        LocalDateTime dataHoraConvertida = LocalDateTime.parse(dataHora, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Cria o evento
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
                dono
        );

        // Salva o evento
        eventoService.salvarEvent(evento);

        return "redirect:/evento/telaInicial";
    }
}
