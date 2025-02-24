package br.com.nlw.events.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.models.Event;
import br.com.nlw.events.models.Indication;
import br.com.nlw.events.models.Subscription;
import br.com.nlw.events.models.User;
import br.com.nlw.events.repositories.SubscriptionRepository;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private EventService eventService;

    public Subscription add(Subscription subscriptionNew) {
        return subscriptionRepository.save(subscriptionNew);
    }

    public Subscription findByEventAndUser(Event event, User user){

        Optional<Subscription> subscription = subscriptionRepository
        .findByEventAndUser(event, user);

        if(!subscription.isPresent()){
            return null;
        }

        return subscription.get();
    } 

    public Optional<Subscription> findById(Integer id) {
        return subscriptionRepository.findById(id);
    }

    public List<Subscription> findAllByEvent(String prettyName) {

        Event event = eventService.findByPrettyName(prettyName);
        return (List<Subscription>) subscriptionRepository.findAllByEvent(event);
    }

    /**
     * /prettyName/subscriptionId
     */
    // public getIndicationLink(){
        
    //     String indicationLink = eventService.findByPrettyName(prettyName)
    // }
}
