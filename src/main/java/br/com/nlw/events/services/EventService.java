package br.com.nlw.events.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Event addNewEvent(Event event){

        event.setPrettyName(event.getTitle().toLowerCase().replaceAll(" ", "-"));

        return this.eventRepository.save(event);
    }

    /**
     * Add new event
     * 
     * @return List Event
    */
    public List<Event> getAllEvents(){
        return (List<Event>) this.eventRepository.findAll();
    }

    /**
     * Add new event
     * 
     * @param String prettyName
     * 
     * @return Event
    */
    public Event getByPrettyName(String prettyName){
        return this.eventRepository.findByPrettyName(prettyName);
    }
}
