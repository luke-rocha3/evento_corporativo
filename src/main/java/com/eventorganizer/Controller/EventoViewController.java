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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/evento")
public class EventoViewController {

    @Autowired
    private EventoService eventoService;

    // Página inicial
    @GetMapping("/pagina")
    public String paginaEventos() {
        return "telaInicial";
    }

    @GetMapping("/telaInicial")
    public String mostrarTelaInicial() {
        return "telaInicial";
    }

    // ==== CADASTRAR EVENTO ====

    @GetMapping("/cadastrar")
    public String exibirFormulario() {
        return "cadastrarEvento";
    }

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
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        if (usuarioLogado == null) {
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

    // ==== EDITAR EVENTO ====

    // Método para evitar erro 404 ao acessar /evento/editar sem id
    @GetMapping("/editar")
    public String editarSemId() {
        return "redirect:/evento/telaInicial";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable int id, Model model) {
        Optional<Evento> optionalEvento = eventoService.buscarPorId(id);
        if (optionalEvento.isEmpty()) {
            return "redirect:/evento/telaInicial";
        }

        Evento evento = optionalEvento.get();
        String dataHoraFormatada = evento.getDataHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

        model.addAttribute("evento", evento);
        model.addAttribute("dataHoraFormatada", dataHoraFormatada);

        return "editarEvento";
    }

    @PostMapping("/editar/{id}")
    public String atualizarEvento(
            @PathVariable int id,
            @RequestParam String nomeEvento,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) String local,
            @RequestParam String dataHora,
            @RequestParam(required = false) String faixaEtaria,
            @RequestParam String periodicidade,
            @RequestParam String espaco,
            @RequestParam(required = false, defaultValue = "0") int capacidade,
            @RequestParam String entrada,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        Optional<Evento> optionalEvento = eventoService.buscarPorId(id);
        if (optionalEvento.isEmpty()) {
            return "redirect:/evento/telaInicial";
        }

        Evento evento = optionalEvento.get();
        LocalDateTime dataHoraConvertida = LocalDateTime.parse(dataHora, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

        evento.setNomeEvento(nomeEvento);
        evento.setDescricao(descricao);
        evento.setLocal(local);
        evento.setDataHora(dataHoraConvertida);
        evento.setFaixaEtaria(faixaEtaria);
        evento.setPeriodicidade(Periodicidade.valueOf(periodicidade));
        evento.setEspaco(Espaco.valueOf(espaco));
        evento.setCapacidade(capacidade);
        evento.setEntrada(Entrada.valueOf(entrada));

        eventoService.salvarEvent(evento);

        return "redirect:/evento/telaInicial";
    }

    // ==== EXCLUIR EVENTO ====

    @GetMapping("/excluir")
    public String confirmarExcluirEvento(@RequestParam int id, Model model) {
        Optional<Evento> evento = eventoService.buscarPorId(id);
        model.addAttribute("evento", evento.orElse(null));
        return "ExcluirEvento";
    }

    @PostMapping("/deletar")
    public String deletarEvento(@RequestParam int id) {
        eventoService.deletarPorId(id);
        return "redirect:/evento/telaInicial";
    }
}
