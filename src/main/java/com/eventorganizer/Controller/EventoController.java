package com.eventorganizer.Controller;

import com.eventorganizer.entity.Evento;
import com.eventorganizer.service.EventoService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller; // <-- Importante!
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // Criar um novo evento
    @PostMapping
    @ResponseBody // <-- necessário agora para retornar JSON aqui
    public ResponseEntity<Evento> salvarEvento(@RequestBody Evento evento) {
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
    public String listarEventos(Model model) {
        List<Evento> eventos = eventoService.listarTodos();
        model.addAttribute("eventos", eventos);
        return "listarEventos"; 
    }

    // Deletar evento por ID
    @DeleteMapping
    @ResponseBody
    public ResponseEntity<Void> deletarEventoPorId(@RequestParam int id) {
        eventoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    // Atualizar evento por ID
    @PutMapping
    @ResponseBody
    public ResponseEntity<Void> atualizarEventoPorId(@RequestParam int id, @RequestBody Evento evento) {
        eventoService.atualizarEvento(id, evento);
        return ResponseEntity.ok().build();
    }
}
