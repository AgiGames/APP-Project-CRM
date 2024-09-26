package com.app.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/data-visualization")
public class DataVisualizationController {

    @PostMapping("/display-students")
    public ResponseEntity<String[]> displayStudents(@RequestParam String adminName,
                                                  @RequestParam String adminRegistrationNumber) {

        String[] result = DataVisualization.displayStudents(adminName, adminRegistrationNumber);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/display-student-folder-from-admin")
    public ResponseEntity<String[]> displayStudentFolderFromAdmin(@RequestParam String adminName,
                                                                 @RequestParam String adminRegistrationNumber,
                                                                 @RequestParam String nameOfStudent,
                                                                 @RequestParam String registrationNumberOfStudent) {

        String[] result = DataVisualization.displayStudentFolderFromAdmin(adminName, adminRegistrationNumber, nameOfStudent, registrationNumberOfStudent);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/display-student-folder-from-path")
    public ResponseEntity<String> displayStudentFolderFromPath(@RequestParam String directoryPath) {

        String result = DataVisualization.displayStudentFolderFromPath(directoryPath);
        return ResponseEntity.ok(result);

    }

}
