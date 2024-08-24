package com.app.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-inputs")
public class UserInputsController {

    @GetMapping("/register-student")
    public ResponseEntity<Boolean> registerStudent(@RequestParam String nameOfStudent,
                                                   @RequestParam String registrationNumberOfStudent,
                                                   @RequestParam String password,
                                                   @RequestParam String adminName,
                                                   @RequestParam String adminRegistrationNumber) {

        Boolean result = UserInputs.registerStudent(nameOfStudent, registrationNumberOfStudent, password, adminName, adminRegistrationNumber);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/remove-student")
    public ResponseEntity<Boolean> removeStudent(@RequestParam String nameOfStudent,
                                                   @RequestParam String registrationNumberOfStudent,
                                                   @RequestParam String adminName,
                                                   @RequestParam String adminRegistrationNumber) {

        Boolean result = UserInputs.removeStudent(nameOfStudent, registrationNumberOfStudent, adminName, adminRegistrationNumber);
        return ResponseEntity.ok(result);

    }

}
