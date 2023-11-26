package com.epam.training.ticketservice.core.Users.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    private User_right user_right;

    public User(String username, String password, User_right user_right) {
        this.name = username;
        this.password = password;
        this.user_right = user_right;
    }

    public enum User_right {
        ADMIN,
        USER
    }
}
