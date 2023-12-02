package com.epam.training.ticketservice.core.user.model;

import com.epam.training.ticketservice.core.user.persistence.User;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private User.Role role;
}