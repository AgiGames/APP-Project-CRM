package com.app.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

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

    @PostMapping("/upload-to-directory")
    public ResponseEntity<Boolean> uploadToDirectory(@RequestParam String nameOfStudent,
                                                 @RequestParam String registrationNumberOfStudent,
                                                 @RequestParam String adminName,
                                                 @RequestParam String adminRegistrationNumber,
                                                 @RequestParam String fileName,
                                                 @RequestParam String fileContentAsString) {
        
        byte[] fileContent = Base64.getDecoder().decode(fileContentAsString);
        Boolean result = UserInputs.uploadToDirectory(nameOfStudent, registrationNumberOfStudent,
                                                     adminName, adminRegistrationNumber, fileName, fileContent);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/get-file-from-directory")
    public ResponseEntity<byte[]> getFileFromDirectory(@RequestParam String nameOfStudent,
                                                     @RequestParam String registrationNumberOfStudent,
                                                     @RequestParam String adminName,
                                                     @RequestParam String adminRegistrationNumber,
                                                     @RequestParam String fileName) {

        try {
            String base64ContentString = UserInputs.getFileFromDirectory(nameOfStudent, registrationNumberOfStudent,
                    adminName, adminRegistrationNumber, fileName);

            byte[] fileContent = Base64.getDecoder().decode(base64ContentString);
            String contentType = "application/octet-stream";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(fileContent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/delete-file-from-directory")
    public ResponseEntity<Boolean> deleteFileFromDirectory(@RequestParam String nameOfStudent,
                                                       @RequestParam String registrationNumberOfStudent,
                                                       @RequestParam String adminName,
                                                       @RequestParam String adminRegistrationNumber,
                                                       @RequestParam String fileName) {

        Boolean result = UserInputs.deleteFileFromDirectory(nameOfStudent, registrationNumberOfStudent,
                adminName, adminRegistrationNumber, fileName);
        return ResponseEntity.ok(result);

    }

}
