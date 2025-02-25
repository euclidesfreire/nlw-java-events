package br.com.nlw.events.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.exceptions.NotFoundException;
import br.com.nlw.events.models.Event;
import br.com.nlw.events.repositories.EventRepository;

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
         .orElseThrow(() -> new NotFoundException("Event not found with id: " + id));
    }

    /**
     * Get Event By Pretty Name
     * 
     * @param String prettyName
     * 
     * @return Event
    */
    public Event findByPrettyName(String prettyName) throws NotFoundException {
        return eventRepository.findByPrettyName(prettyName)
        .orElseThrow(() -> new NotFoundException("Event not found: "  + prettyName));
    }

    
}
