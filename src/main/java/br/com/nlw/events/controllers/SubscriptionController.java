package br.com.nlw.events.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.nlw.events.exceptions.AlreadyExistsException;
import br.com.nlw.events.exceptions.NotFoundException;
import br.com.nlw.events.models.Event;
import br.com.nlw.events.models.Indication;
import br.com.nlw.events.models.Subscription;
import br.com.nlw.events.models.User;
import br.com.nlw.events.services.EventService;
import br.com.nlw.events.services.IndicationService;
import br.com.nlw.events.services.RankingService;
import br.com.nlw.events.services.SubscriptionService;
import br.com.nlw.events.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private IndicationService indicationService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private RankingService rankingService;

    
    @PostMapping("/subscription/{prettyName}")
    public ResponseEntity<Object> postSubscription(
        @PathVariable String prettyName, 
        @RequestBody User user
    ){

        try {

            List<Object> response = new ArrayList<Object>();

            Event event = eventService.findByPrettyName(prettyName);

            Subscription subscription = subscriptionService.add(event, user);

            String indicationUrl = indicationService.getUrl(prettyName, subscription.getId());

            response.add(indicationUrl)
            response.add(subscription.getId())   

            return ResponseEntity.ok().body(response);

        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    @PostMapping("/subscription/{prettyName}/{subscriptionId}")
    public ResponseEntity<Object> postSubscriptionIndication(
        @PathVariable String prettyName, 
        @PathVariable Integer subscriptionId, 
        @RequestBody User userNew
    ){
        List<Object> subscriptionResponse = new ArrayList<Object>();
        String indicationLink = "subscription/";

        Event event = eventService.findByPrettyName(prettyName);

        if( Objects.isNull(event) ){
            return ResponseEntity.badRequest().body("Evento não existe.");
        }

        User user = userService.add(userNew);

        Subscription subscription = subscriptionService.findByEventAndUser(event, user);

        if( Objects.nonNull(subscription) ){

            indicationLink = event.getPrettyName() + "/" + subscription.getId();

            subscriptionResponse.add(subscription.getId());
            subscriptionResponse.add(indicationLink);

            return ResponseEntity.badRequest().body(subscriptionResponse);
        }

        Subscription subscriptionNew = new Subscription();
        subscriptionNew.setEvent(event);
        subscriptionNew.setUser(user);

        Subscription subscriptionAdd = subscriptionService.add(subscriptionNew);

        Subscription subscriptionIndication = subscriptionService.findById(subscriptionId).get();

        Indication indication = new Indication();
        indication.setSubscription(subscriptionIndication);
        indication.setUser(userNew);

        indicationService.add(indication);

        indicationLink = event.getPrettyName() + "/" + subscriptionAdd.getId();

        subscriptionResponse.add(subscriptionAdd.getId());
        subscriptionResponse.add(subscriptionAdd);

        return ResponseEntity.ok().body(subscriptionResponse);
    }

    @GetMapping("/subscription/{prettyName}")
    public List<Subscription>  getSubscription(@PathVariable String prettyName){
         return subscriptionService.findAllByEvent(prettyName);
    }

    @GetMapping("/subscription/{prettyName}/ranking")
    public List<Object>  getRanking(@PathVariable String prettyName){
        return rankingService.getRanking(prettyName);
    }

    @GetMapping("/subscription/{prettyName}/{subscriptionId}/ranking")
    public List<Object>  getRanking(
        @PathVariable String prettyName,
        @PathVariable Integer subscriptionId
    ){

        return rankingService.getRanking(prettyName);
    }
}
