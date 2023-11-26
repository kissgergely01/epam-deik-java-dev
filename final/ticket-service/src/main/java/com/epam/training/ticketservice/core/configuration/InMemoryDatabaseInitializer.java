package com.epam.training.ticketservice.core.configuration;


import com.epam.training.ticketservice.core.Users.persistence.User;
import com.epam.training.ticketservice.core.Users.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Profile("!prod")
public class InMemoryDatabaseInitializer {

    private final UserRepository userRepository;


    @PostConstruct
    public void init() {
        User admin = new User("admin", "admin", User.User_right.ADMIN);
        userRepository.save(admin);
        User teszt = new User("teszt", "teszt", User.User_right.USER);
        userRepository.save(teszt);



    }
}