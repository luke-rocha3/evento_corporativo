package com.eventorganizer.Controller;

import com.eventorganizer.entity.Evento;
import com.eventorganizer.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // Criar um novo evento
    @PostMapping
    public ResponseEntity<Evento> salvarEvento(@RequestBody Evento evento) {
        Evento eventoSalvo = eventoService.salvarEvent(evento);
        System.out.println("Evento salvo com ID: " + eventoSalvo.getId());
        return ResponseEntity.status(201).body(eventoSalvo); // Retorna 201 Created com o evento salvo
    }

    // Buscar evento por ID
    @GetMapping
    public ResponseEntity<Evento> buscarEventoPorId(@RequestParam int id) {
        Evento evento = eventoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Evento com ID " + id + " não encontrado"));
        return ResponseEntity.ok(evento);
    }

    // Deletar evento por ID
    @DeleteMapping
    public ResponseEntity<Void> deletarEventoPorId(@RequestParam int id) {
        eventoService.deletarPorId(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Atualizar evento por ID
    @PutMapping
    public ResponseEntity<Void> atualizarEventoPorId(@RequestParam int id, @RequestBody Evento evento) {
        eventoService.atualizarEvento(id, evento);
        return ResponseEntity.ok().build();
    }
}
