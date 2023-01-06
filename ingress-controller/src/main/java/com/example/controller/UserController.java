package com.example.controller;

import com.example.service.ConsumingService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "The User API")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final ConsumingService userConsumingService;

    @Operation(summary = "Consumes user")
    @ApiResponses(
        @ApiResponse(
            responseCode = "200",
            description = "User was successfully consumed"
        )
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority(@authorityConfiguration.getSuperAuthorities()) or hasAuthority('users.WRITE')")
    public void doConsume(@RequestBody JsonNode user) {
        userConsumingService.doConsume(user);
    }

}