package com.kadirgurturk.emailSender.controller;

import com.kadirgurturk.emailSender.buisness.response.HttpResponse;
import com.kadirgurturk.emailSender.domain.User;
import com.kadirgurturk.emailSender.buisness.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<HttpResponse> createUser(@RequestBody User user)
    {

        userService.saveUser(user);

        return ResponseEntity.created(URI.create("")).body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("user", user))
                        .message("User created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }


    @GetMapping
    public ResponseEntity<HttpResponse> confirmAccount(@RequestParam("token") String token)
    {

        boolean isSuccess = userService.isExistToken(token);

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .data(Map.of("Success", isSuccess))
                        .message("Account Verified")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

}
