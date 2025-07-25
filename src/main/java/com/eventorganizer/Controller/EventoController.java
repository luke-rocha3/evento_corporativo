package com.eventorganizer.Controller;

import com.eventorganizer.entity.Evento;
import com.eventorganizer.entity.Usuario;
import com.eventorganizer.service.EventoService;

import jakarta.validation.Valid;  // Import do @Valid
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // Criar um novo evento - com @Valid para validar o JSON recebido
    @PostMapping
    @ResponseBody
    public ResponseEntity<Evento> salvarEvento(@Valid @RequestBody Evento evento) {
        Evento eventoSalvo = eventoService.salvarEvent(evento);
        System.out.println("Evento salvo com ID: " + eventoSalvo.getId());
        return ResponseEntity.status(201).body(eventoSalvo);
    }

    // Buscar evento por ID
    @GetMapping
    @ResponseBody
    public ResponseEntity<Evento> buscarEventoPorId(@RequestParam int id) {
        Evento evento = eventoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Evento com ID " + id + " não encontrado"));
        return ResponseEntity.ok(evento);
    }

    @GetMapping("/listar")
    public String listarEventos(Model model, @AuthenticationPrincipal Usuario usuarioLogado) {
        model.addAttribute("eventos", eventoService.listarTodos());
        model.addAttribute("usuarioLogado", usuarioLogado);
        return "listarEventos";
    }

    // Deletar evento por ID
    @DeleteMapping
    @ResponseBody
    public ResponseEntity<Void> deletarEventoPorId(@RequestParam int id) {
        eventoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    // Atualizar evento por ID - com @Valid para validar o JSON recebido
    @PutMapping
    @ResponseBody
    public ResponseEntity<Void> atualizarEventoPorId(@RequestParam int id, @Valid @RequestBody Evento evento) {
        eventoService.atualizarEvento(id, evento);
        return ResponseEntity.ok().build();
    }
}
