package com.harikart.userservice.controller;

import com.harikart.userservice.dtos.LoginRequestDto;
import com.harikart.userservice.dtos.LogoutRequestDto;
import com.harikart.userservice.dtos.SignUpRequestDto;
import com.harikart.userservice.dtos.UserDto;
import com.harikart.userservice.model.Token;
import com.harikart.userservice.model.User;
import com.harikart.userservice.service.UserService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public User signUp(@RequestBody SignUpRequestDto request) {
        // no need to hash password for now
        // just store user as is in the db
        // for now no need to have email verification either
        String email = request.getEmail();
        String password = request.getPassword();
        String name = request.getName();


        return userService.signUp(name, email, password);
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto request){
        return userService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request){
        userService.logout(request.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validateToken/{token}")
    public UserDto validateToke(@PathVariable("token") @NonNull String token){
        return UserDto.from(userService.validateToken(token));
    }
}
