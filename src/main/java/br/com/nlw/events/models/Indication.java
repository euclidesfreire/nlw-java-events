package br.com.nlw.events.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "indication")
public class Indication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "subscription_indication_id", nullable = false)
    private Subscription subscriptionIndication;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Subscription getSubscriptionIndication() {
        return subscriptionIndication;
    }

    public void setSubscriptionIndication(Subscription subscriptionIndication) {
        this.subscriptionIndication = subscriptionIndication;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
