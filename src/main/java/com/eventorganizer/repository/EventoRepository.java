package com.eventorganizer.repository;

import com.eventorganizer.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

}
// Interface responsável por acessar e manipular os dados da entidade Event no
// banco de dados.
// Ao estender JpaRepository<Event, Integer>, esta interface herda
// automaticamente
// diversos métodos prontos fornecidos pelo Spring Data JPA, como:
//
// - findAll() → busca todos os eventos.
// - findById(id) → busca um evento pelo ID.
// - save(event) → salva um novo evento ou atualiza um existente.
// - deleteById(id) → remove um evento pelo ID.
//
// Isso elimina a necessidade de escrever SQL ou implementar esses métodos
// manualmente.
// Também permite definir consultas personalizadas criando métodos com nomes
// específicos.
//
// Essa interface será utilizada em services ou controllers para interagir com o
// banco
// de forma simples, segura e eficiente.