package com.app.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-authenticator")
public class UserAuthenticatorController {

    private final UserAuthenticator userAuthenticator;

    @Autowired
    public UserAuthenticatorController(UserAuthenticator userAuthenticator) {

        this.userAuthenticator = userAuthenticator;

    }

    @PostMapping("/login")
    public ResponseEntity<Integer> login(@RequestParam String username,
                                         @RequestParam String registrationNumber,
                                         @RequestParam String password) {

        Integer result = userAuthenticator.login(username, registrationNumber, password);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/get-username")
    public ResponseEntity<String> getUsername(@RequestParam String registrationNumber) {

        String result = userAuthenticator.getUserName(registrationNumber);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/get-registration-number")
    public ResponseEntity<String> getRegistrationNumber(@RequestParam String registrationNumber) {

        String result = userAuthenticator.getRegistrationNumber();
        return ResponseEntity.ok(result);

    }

    @PostMapping("/get-admin-name")
    public ResponseEntity<String> getAdminName(@RequestParam String registrationNumber) {

        String result = userAuthenticator.getAdminName(registrationNumber);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/get-admin-registration-number")
    public ResponseEntity<String> getAdminRegistrationNumber(@RequestParam String registrationNumber) {

        String result = userAuthenticator.getAdminRegistrationNumber(registrationNumber);
        return ResponseEntity.ok(result);

    }

}
