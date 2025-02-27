package br.com.nlw.events.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.models.Indication;
import br.com.nlw.events.models.Subscription;
import br.com.nlw.events.repositories.IndicationRepository;

@Service
public class IndicationService {

    @Autowired
    IndicationRepository indicationRepository;

    @Autowired
    SubscriptionService subscriptionService;

    public void add(Indication indication){

        //Get Indication Subscription 
        Subscription subscription =  indication.getSubscriptionIndication();

        //indicationCount + 1
        Integer indicationCount = subscription.getIndicationCount() + 1;

        subscription.setIndicationCount(indicationCount);

        //Update subscription for add indicationCount
        subscriptionService.save(subscription);

        indicationRepository.save(indication);
    }

    public String getUrl(String eventPrettyName, Integer subscriptionId){
        String url = "/subscription/" + eventPrettyName + "/" + subscriptionId;

        return url;
    }
}
