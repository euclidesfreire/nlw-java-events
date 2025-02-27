package br.com.nlw.events.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.nlw.events.exceptions.AlreadyExistsException;
import br.com.nlw.events.exceptions.NotFoundException;
import br.com.nlw.events.models.Indication;
import br.com.nlw.events.models.Subscription;
import br.com.nlw.events.models.User;
import br.com.nlw.events.services.IndicationService;
import br.com.nlw.events.services.RankingService;
import br.com.nlw.events.services.SubscriptionService;


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
    private RankingService rankingService;

    
    @PostMapping("/subscription/{prettyName}")
    public ResponseEntity<Object> postSubscription(
        @PathVariable String prettyName, 
        @RequestBody User user
    ){

        try {

            List<Object> response = new ArrayList<Object>();

            Subscription subscription = subscriptionService.add(prettyName, user);

            String indicationUrl = indicationService.getUrl(prettyName, subscription.getId());

            response.add(indicationUrl);
            response.add(subscription.getId()); 

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
        @PathVariable Integer subscriptionIndicationId, 
        @RequestBody User user
    ){

        try {

            List<Object> response = new ArrayList<Object>();

            //Add new subscription by subscription indication
            Subscription subscription = subscriptionService
            .addByIndication(prettyName, user, subscriptionIndicationId);

            String indicationUrl = indicationService.getUrl(prettyName, subscription.getId());

            response.add(indicationUrl);
            response.add(subscription.getId()); 

            return ResponseEntity.ok().body(response);

        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
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
