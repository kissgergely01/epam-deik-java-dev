package com.epam.training.ticketservice.core.Users;

import com.epam.training.ticketservice.core.Users.model.UserDto;
import com.epam.training.ticketservice.core.Users.persistence.User;
import com.epam.training.ticketservice.core.Users.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private UserDto loggedInUser = null;

    @Override
    public Optional<UserDto> login(String name, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(name, password);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        loggedInUser = new UserDto(user.get().getName(), user.get().getPassword(), user.get().getUser_right());
        return describe();
    }
    @Override
    public Optional<UserDto> adminLogin(String name, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(name, password);

        if (user.isEmpty() || user.get().getUser_right() != User.User_right.ADMIN) {
            return Optional.empty();
        }
        loggedInUser = new UserDto(user.get().getName(), user.get().getPassword(), user.get().getUser_right());
        return describe();
    }

    @Override
    public Optional<UserDto> logout() {
        Optional<UserDto> previouslyLoggedInUser = describe();
        loggedInUser = null;
        return previouslyLoggedInUser;
    }

    @Override
    public Optional<UserDto> describe() {
        return Optional.ofNullable(loggedInUser);
    }

    @Override
    public void registerUser(String username, String password) {
        User newuser = new User(username, password, User.User_right.USER);
        userRepository.save(newuser);
    }
}
