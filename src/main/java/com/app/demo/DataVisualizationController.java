package com.app.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/data-visualization")
public class DataVisualizationController {

    @PostMapping("/api/display-students")
    public ResponseEntity<String> displayStudents(@RequestParam String adminName) {

        String result = DataVisualization.displayStudents(adminName);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/api/display-students-folder-from-admin")
    public ResponseEntity<String> displayStudentsFolderFromAdmin(@RequestParam String adminName,
                                                           @RequestParam String nameOfStudent,
                                                           @RequestParam String registrationNumberOfStudent) {

        String result = DataVisualization.displayStudentFolderFromAdmin(adminName, nameOfStudent, registrationNumberOfStudent);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/api/display-students-folder-from-path")
    public ResponseEntity<String> displayStudentsFolderFromPath(@RequestParam String directoryPath) {

        String result = DataVisualization.displayStudentFolderFromPath(directoryPath);
        return ResponseEntity.ok(result);

    }

}
