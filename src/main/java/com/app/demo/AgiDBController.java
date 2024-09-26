package com.app.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/agi-db")
public class AgiDBController {

    @PostMapping("/write")
    public ResponseEntity<Boolean> write(@RequestParam JSONDocument jsonDocument,
                                         @RequestParam String databasePath,
                                         @RequestParam String collectionName) {

        AgiDB agiDB = new AgiDB(databasePath, collectionName);
        Boolean result = agiDB.write(jsonDocument);
        return ResponseEntity.ok(result);

    }

}
