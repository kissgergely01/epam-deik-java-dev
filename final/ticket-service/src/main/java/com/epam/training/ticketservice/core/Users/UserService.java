package com.epam.training.ticketservice.core.Users;

import com.epam.training.ticketservice.core.Users.model.UserDTO;

import java.util.Optional;

public interface UserService {

    Optional<UserDTO> login(String username, String password);

    Optional<UserDTO> logout();
    Optional<UserDTO> adminLogin(String username, String password);
    Optional<UserDTO> describe();

    void registerUser(String username, String password);
}