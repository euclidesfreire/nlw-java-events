package br.com.nlw.events.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.nlw.events.models.Event;

public interface EventRepository extends CrudRepository<Event, Integer> {
    public Event findByPrettyName(String PrettyName);
}
