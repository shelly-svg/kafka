package com.example.controller;

import com.example.service.ConsumingService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final ConsumingService userConsumingService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus consume(@RequestBody JsonNode user) {
        userConsumingService.doConsume(user);
        return HttpStatus.OK;
    }

}