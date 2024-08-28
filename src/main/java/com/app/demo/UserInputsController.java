package com.app.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-inputs")
public class UserInputsController {

    @PostMapping("/register-student")
    public ResponseEntity<Boolean> registerStudent(@RequestParam String nameOfStudent,
                                                   @RequestParam String registrationNumberOfStudent,
                                                   @RequestParam String password,
                                                   @RequestParam String adminName,
                                                   @RequestParam String adminRegistrationNumber) {

        Boolean result = UserInputs.registerStudent(nameOfStudent, registrationNumberOfStudent, password, adminName, adminRegistrationNumber);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/remove-student")
    public ResponseEntity<Boolean> removeStudent(@RequestParam String nameOfStudent,
                                                   @RequestParam String registrationNumberOfStudent,
                                                   @RequestParam String adminName,
                                                   @RequestParam String adminRegistrationNumber) {

        Boolean result = UserInputs.removeStudent(nameOfStudent, registrationNumberOfStudent, adminName, adminRegistrationNumber);
        return ResponseEntity.ok(result);

    }

}
