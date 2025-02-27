package br.com.nlw.events.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.exceptions.AlreadyExistsException;
import br.com.nlw.events.exceptions.NotFoundException;
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

    @Autowired
    private UserService userService;

    @Autowired
    private IndicationService indicationService;

    public Subscription add(String eventPrettyName, User userNew) {

        //check e find event
        Event event = eventService.findByPrettyName(eventPrettyName);

        //Create user or if Already Exists, get user
        User user = userService.add(userNew);

        if(subscriptionRepository.findByEventAndUser(event, user).isPresent()){
            throw new AlreadyExistsException("Subscription Already Exists.");
        }

        Subscription subscriptionNew = new Subscription();
        subscriptionNew.setEvent(event);
        subscriptionNew.setUser(user);

        return subscriptionRepository.save(subscriptionNew);
    }

    public Subscription addByIndication(
        String eventPrettyName, 
        User user, 
        Integer subscriptionIndicationId
    ){
        Subscription subscription = add(eventPrettyName, user);

        //Get subscription indication
        Subscription subscriptionIndication = subscriptionRepository
        .findById(subscriptionIndicationId).get();

        Indication indication = new Indication();
        indication.setSubscription(subscriptionIndication);
        indication.setUser(user);

        indicationService.add(indication);

        return subscription;
    }

    public Subscription save(Subscription subscriptionNew) {
        return subscriptionRepository.save(subscriptionNew);
    }

    public Subscription findByEventAndUser(Event event, User user) {

        Subscription subscription = subscriptionRepository
                .findByEventAndUser(event, user)
                .orElseThrow(() -> new NotFoundException("Subscription not found."));

        return subscription;
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

    // String indicationLink = eventService.findByPrettyName(prettyName)
    // }
}
