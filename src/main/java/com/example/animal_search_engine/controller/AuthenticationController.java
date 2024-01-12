package com.example.animal_search_engine.controller;

import com.example.animal_search_engine.dto.request.SignUpRequest;
import com.example.animal_search_engine.dto.request.SigninRequest;
import com.example.animal_search_engine.dto.responce.JwtAuthenticationResponse;
import com.example.animal_search_engine.exception.SuccessMessage;
import com.example.animal_search_engine.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignUpRequest request) {
        authenticationService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessMessage.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Consumer added")
                        .datetime(LocalDateTime.now())
                        .build());
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }


    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }


    @PostMapping("/block-user")
    public ResponseEntity<Object> blockUser(@RequestParam("email") String email) {
        authenticationService.blockUser(email);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessMessage.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Consumer blocked ")
                        .datetime(LocalDateTime.now())
                        .build());
    }

    @PostMapping("/unblock-user")
    public ResponseEntity<Object> unblockUser(@RequestParam("email") String email) {
        authenticationService.unblockUser(email);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessMessage.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Consumer unblocked")
                        .datetime(LocalDateTime.now())
                        .build());
    }



}

