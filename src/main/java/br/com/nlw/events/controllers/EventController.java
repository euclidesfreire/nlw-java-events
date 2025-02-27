package br.com.nlw.events.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.nlw.events.exceptions.NotFoundException;
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
    
    /**
     * 
     * @param event
     * @return Event
     */
    @PostMapping("/events")
    public ResponseEntity<?> postEvent(@RequestBody Event event) {

        try {
            return ResponseEntity.ok().body(this.eventService.add(event));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }
    
    @GetMapping("/events")
    public List<Event> getAllEvents(){
        return (List<Event>) this.eventService.findAll();
    }

    /**
     * 
     * @param id
     * @return Ev
     */
    @GetMapping("/events/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Integer id){
        try {
            Event event = this.eventService.findById(id);

            return ResponseEntity.ok().body(event);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * 
     * @param prettyName
     * @return Event
    */
    @GetMapping("/events/{prettyName}")
    public ResponseEntity<?> getEventByPrettyName(@PathVariable String prettyName){
        try {
            Event event =  this.eventService.findByPrettyName(prettyName);

            return ResponseEntity.ok().body(event);
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }   
}
