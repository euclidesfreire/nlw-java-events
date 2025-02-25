package br.com.nlw.events.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.exceptions.NotFoundException;
import br.com.nlw.events.models.User;
import br.com.nlw.events.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User add(User userNew) {

        Optional<User> user = userRepository.findByEmail(userNew.getEmail());

        if (user.isPresent()) {
            return user.get();
        }

        return userRepository.save(userNew);

    }

    public User findById(Integer id){
        return userRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }
}
