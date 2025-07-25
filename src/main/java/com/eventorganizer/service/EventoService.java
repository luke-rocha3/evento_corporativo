package com.eventorganizer.service;

import com.eventorganizer.entity.Evento;
import com.eventorganizer.entity.Usuario;
import com.eventorganizer.repository.EventoRepository;
import com.eventorganizer.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    // Criar um evento
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Evento salvarEvent(Evento evento) {
        Integer idUsuario = evento.getDono().getId();
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + idUsuario + " não encontrado"));

        evento.setDono(usuario); // associa o objeto completo
        return eventoRepository.save(evento);
    }

    // Atualizar evento existente
    public Evento atualizarEvento(Integer id, Evento novoEvento) {
        Evento eventoExistente = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento com ID " + id + " não encontrado"));

        eventoExistente.setNomeEvento(novoEvento.getNomeEvento());
        eventoExistente.setDescricao(novoEvento.getDescricao());
        eventoExistente.setLocal(novoEvento.getLocal());
        eventoExistente.setDataHora(novoEvento.getDataHora());
        eventoExistente.setFaixaEtaria(novoEvento.getFaixaEtaria());
        eventoExistente.setPeriodicidade(novoEvento.getPeriodicidade());
        eventoExistente.setEspaco(novoEvento.getEspaco());
        eventoExistente.setCapacidade(novoEvento.getCapacidade());
        eventoExistente.setEntrada(novoEvento.getEntrada());
        eventoExistente.setDono(novoEvento.getDono());

        return eventoRepository.save(eventoExistente);
    }

    // Buscar todos os eventos
    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    // Buscar evento por ID
    public Optional<Evento> buscarPorId(Integer id) {
        return eventoRepository.findById(id);
    }

    // Deletar evento pelo ID
    public void deletarPorId(Integer id) {
        eventoRepository.deleteById(id);
    }

    public Object buscarTodos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarTodos'");
    }
}
