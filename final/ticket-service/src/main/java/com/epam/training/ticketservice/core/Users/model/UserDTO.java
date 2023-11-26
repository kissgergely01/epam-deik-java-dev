package com.epam.training.ticketservice.core.Users.model;

import com.epam.training.ticketservice.core.Users.persistence.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class UserDTO{
    private String name;
    private String password;
    private User.User_right user_right;
    public UserDTO(String username, String password, User.User_right user_right) {
        this.name = username;
        this.password = password;
        this.user_right = user_right;
    }
}