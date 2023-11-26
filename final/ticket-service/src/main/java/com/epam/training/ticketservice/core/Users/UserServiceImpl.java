package com.epam.training.ticketservice.core.Users;

import com.epam.training.ticketservice.core.Users.model.UserDTO;
import com.epam.training.ticketservice.core.Users.persistence.User;
import com.epam.training.ticketservice.core.Users.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private UserDTO loggedInUser = null;

    @Override
    public Optional<UserDTO> login(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        loggedInUser = new UserDTO(user.get().getName(), user.get().getPassword(), user.get().getUser_right());
        return describe();
    }
    @Override
    public Optional<UserDTO> adminLogin(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);

        if (user.isEmpty() || user.get().getUser_right() != User.User_right.ADMIN) {
            return Optional.empty();
        }

        loggedInUser = new UserDTO(user.get().getName(), user.get().getPassword(), user.get().getUser_right());

        return describe();
    }


    @Override
    public Optional<UserDTO> logout() {
        Optional<UserDTO> previouslyLoggedInUser = describe();
        loggedInUser = null;
        return previouslyLoggedInUser;
    }

    @Override
    public Optional<UserDTO> describe() {
        return Optional.ofNullable(loggedInUser);
    }

    @Override
    public void registerUser(String username, String password) {
        User user = new User(username, password, User.User_right.USER);
        userRepository.save(user);
    }
}
