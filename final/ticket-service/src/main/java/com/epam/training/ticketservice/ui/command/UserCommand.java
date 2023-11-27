package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.Users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class UserCommand {

    private final UserService userService;

    @ShellMethod(key = "sign out", value = "User logout")
    public String logout() {
        return userService.logout()
                .map(userDto -> userDto + " is logged out!")
                .orElse("You need to login first!");
    }

    @ShellMethod(key = "sign in privileged", value = "sign in privileged")
    public String adminLogin(String username, String password){
        return userService.adminLogin(username,password)
                .map(userDto -> userDto + " is successfully logged in!")
                .orElse("Login failed due to incorrect credentials");
    }
    @ShellMethod(key = "user login", value = "User login")
    public String login(String username, String password) {
        return userService.login(username, password)
                .map(userDto -> userDto + " is successfully logged in!")
                .orElse("No such username or password!");
    }

    @ShellMethod(key = "describe account", value = "Get user information")
    public String description(){
        return userService.describe()
                .map(userDto -> "Signed in with privileged account '"+ userDto.getName()+"'")
                .orElse("You are not signed in");
    }

    @ShellMethod(key = "user register", value = "User registration")
    public String registerUser(String userName, String password) {
        try {
            userService.registerUser(userName, password);
            return "Registration was successful!";
        } catch (Exception e) {
            return "Registration failed!";
        }
    }
}