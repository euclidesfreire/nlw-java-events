package br.com.nlw.events.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.nlw.events.dto.SubscriptionResponseDTO;
import br.com.nlw.events.exceptions.AlreadyExistsException;
import br.com.nlw.events.exceptions.NotFoundException;
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

    /**
     * Post Subscription
     * 
     * @param prettyName
     * @param user
     * @return
     */
    @PostMapping("/subscription/{prettyName}")
    public ResponseEntity<?> postSubscription(
        @PathVariable String prettyName, 
        @RequestBody User user
    ){
        try {

            Subscription subscription = subscriptionService.add(prettyName, user);

            String indicationUrl = indicationService.getUrl(prettyName, subscription.getId());

            return ResponseEntity.ok()
            .body(new SubscriptionResponseDTO(subscription.getId(), indicationUrl));

        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * Post subscription by subscriptionIndicationId
     * 
     * @param prettyName
     * @param subscriptionIndicationId
     * @param user
     * @return
     */
    @PostMapping("/subscription/{prettyName}/{subscriptionIndicationId}")
    public ResponseEntity<?> postSubscriptionIndication(
        @PathVariable String prettyName, 
        @PathVariable Integer subscriptionIndicationId, 
        @RequestBody User user
    ){
        try {
            //Add new subscription by subscription indication
            Subscription subscription = subscriptionService
            .addByIndication(prettyName, user, subscriptionIndicationId);

            String indicationUrl = indicationService.getUrl(prettyName, subscription.getId());

            return ResponseEntity.ok()
            .body(new SubscriptionResponseDTO(subscription.getId(), indicationUrl));

        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * Get subscription
     * 
     * @param prettyName
     * @return List<Subscription>
     */
    @GetMapping("/subscription/{prettyName}")
    public ResponseEntity<?>  getSubscription(@PathVariable String prettyName){
         try {
            return ResponseEntity.ok().body(subscriptionService.findAllByEvent(prettyName));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * Get Ranking Complete 
     * 
     * @param prettyName
     * @return List<Subscription>
     */
    @GetMapping("/subscription/{prettyName}/ranking")
    public ResponseEntity<?>  getRanking(@PathVariable String prettyName){
        try {
            return ResponseEntity.ok().body(rankingService.findRanking(prettyName));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * Get Ranking by Subscription
     * 
     * @param prettyName
     * @param subscriptionId
     * @return Subscription
     */
    @GetMapping("/subscription/{prettyName}/{subscriptionId}/ranking")
    public ResponseEntity<?> getRankingBySubscription(
            @PathVariable String prettyName,
            @PathVariable Integer subscriptionId) {
        try {
            return ResponseEntity.ok().body(rankingService.findRankingBySubscription(prettyName, subscriptionId));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
