package br.com.nlw.events.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.nlw.events.models.Event;
import br.com.nlw.events.services.EventService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class EventController {

    @Autowired
    private EventService eventService;  
    
    @PostMapping("/events")
    public Event postEvent(@RequestBody Event event) {
        return this.eventService.addNewEvent(event);
    }
    
    @GetMapping("/events")
    public List<Event> getAllEvents(){
        return (List<Event>) this.eventService.getAllEvents();
    }

    @GetMapping("/events/{prettyName}")
    public ResponseEntity<Event> getEventByPrettyName(@PathVariable String prettyName){
        Event event =  this.eventService.getByPrettyName(prettyName);

        if(  Objects.isNull(event) ){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(event);
    }   
}
