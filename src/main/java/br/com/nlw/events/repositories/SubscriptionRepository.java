package br.com.nlw.events.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.nlw.events.models.Event;
import br.com.nlw.events.models.Subscription;
import br.com.nlw.events.models.User;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    public Iterable<Subscription> findAllByEvent(Event event);
    public Optional<Subscription> findByEventAndUser(Event event, User user);
}
