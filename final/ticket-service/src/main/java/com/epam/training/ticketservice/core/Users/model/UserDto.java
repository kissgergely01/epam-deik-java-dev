package com.epam.training.ticketservice.core.Users.model;

import com.epam.training.ticketservice.core.Users.persistence.User;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private String password;
    private User.User_right user_right;
}