package com.eventorganizer.service;

import com.eventorganizer.entity.Event;
import com.eventorganizer.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventoRepository eventoRepository;

    // Criar ou atualizar um evento
    public Event salvar(Event event) {
        return eventoRepository.save(event);
    }

    // Buscar todos os eventos
    public List<Event> listarTodos() {
        return eventoRepository.findAll();
    }

    // Buscar evento por ID
    public Optional<Event> buscarPorId(Integer id) {
        return eventoRepository.findById(id);
    }

    // Deletar evento pelo ID
    public void deletarPorId(Integer id) {
        eventoRepository.deleteById(id);
    }
}
