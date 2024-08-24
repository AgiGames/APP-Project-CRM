package com.app.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agi-db")
public class AgiDBController {

    @GetMapping("/write")
    public ResponseEntity<Boolean> write(@RequestParam JSONDocument jsonDocument,
                                         @RequestParam String databasePath,
                                         @RequestParam String collectionName) {

        AgiDB agiDB = new AgiDB(databasePath, collectionName);
        Boolean result = agiDB.write(jsonDocument);
        return ResponseEntity.ok(result);

    }

}
