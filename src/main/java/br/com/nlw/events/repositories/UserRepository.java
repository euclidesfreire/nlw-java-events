package br.com.nlw.events.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.nlw.events.models.User;


public interface UserRepository extends CrudRepository<User,Integer> {
    public Optional<User> findByEmail(String email);
}
