package br.com.nlw.events.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.nlw.events.models.Indication;
import br.com.nlw.events.models.Subscription;
import br.com.nlw.events.models.User;

public interface IndicationRepository extends CrudRepository<Indication, Integer> {
        public Optional<Indication> findBySubscriptionAndUser(Subscription subscription, User user);

}
