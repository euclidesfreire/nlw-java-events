package br.com.nlw.events.services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.models.Subscription;

@Service
public class RankingService {
    
    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    EventService eventService;

    public List<Object> getRanking(String prettyName){
        
            List<Object> ranking = subscriptionService.findAllByEvent(prettyName).stream()
            .sorted(Comparator.comparing((Subscription s)-> s.getIndicationCount()).reversed())
            //.map(s -> Map.entry(s.getUser().getName(), s.getIndicationCount()))
            .collect(Collectors.toList());

            return ranking;
    }
}
