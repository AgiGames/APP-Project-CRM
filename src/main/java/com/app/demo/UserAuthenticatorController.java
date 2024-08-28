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

    @GetMapping("/get-username")
    public ResponseEntity<String> getUsername() {

        String result = userAuthenticator.getUserName();
        return ResponseEntity.ok(result);

    }

    @GetMapping("/get-registration-number")
    public ResponseEntity<String> getRegistrationNumber() {

        String result = userAuthenticator.getRegistrationNumber();
        return ResponseEntity.ok(result);

    }

    @GetMapping("/get-admin-name")
    public ResponseEntity<String> getAdminName() {

        String result = userAuthenticator.getAdminName();
        return ResponseEntity.ok(result);

    }

    @GetMapping("/get-admin-registration-number")
    public ResponseEntity<String> getAdminRegistrationNumber() {

        String result = userAuthenticator.getAdminRegistrationNumber();
        return ResponseEntity.ok(result);

    }

}
