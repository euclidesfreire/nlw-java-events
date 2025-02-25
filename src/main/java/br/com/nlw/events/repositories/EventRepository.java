package br.com.nlw.events.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.nlw.events.models.Event;

public interface EventRepository extends CrudRepository<Event, Integer> {
    public Optional<Event> findByPrettyName(String PrettyName);
}
