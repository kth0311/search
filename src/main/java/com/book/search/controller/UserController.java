package com.book.search.controller;

import com.book.search.controller.dto.UserDto;
import com.book.search.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    @NonNull
    private final UserService userService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registration(@Valid @RequestBody UserDto user) {
        return new UserDto(userService.register(user.getUsername(), user.getPassword()));
    }
}
