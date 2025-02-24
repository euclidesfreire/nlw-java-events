package br.com.nlw.events.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.models.Event;
import br.com.nlw.events.repositories.EventRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    /**
     * Add new event
     * 
     * @param Event envet
     * 
     * @return Event envet
    */
    public Event add(Event event){

        event.setPrettyName(event.getTitle().toLowerCase().replaceAll(" ", "-"));

        return this.eventRepository.save(event);
    }

    /**
     * Get all events
     * 
     * @return List Event
    */
    public List<Event> findAll(){
        return (List<Event>) this.eventRepository.findAll();
    }

    /**
     * Get Event By Id
     * 
     * @param String prettyName
     * 
     * @return Event
    */
    public Event findById(Integer id){

        return eventRepository.findById(id)
         .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado!"));
    }

    /**
     * Get Event By Pretty Name
     * 
     * @param String prettyName
     * 
     * @return Event
    */
    public Event findByPrettyName(String prettyName){
        return eventRepository.findByPrettyName(prettyName);
    }
}
