package com.epam.training.ticketservice.core.Users;

import com.epam.training.ticketservice.core.Users.model.UserDto;
import java.util.Optional;

public interface UserService {
    Optional<UserDto> login(String username, String password);
    Optional<UserDto> logout();
    Optional<UserDto> adminLogin(String username, String password);
    Optional<UserDto> describe();

    void registerUser(String username, String password);
}