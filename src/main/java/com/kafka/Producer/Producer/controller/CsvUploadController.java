package com.kafka.Producer.Producer.controller;

import com.kafka.Producer.Producer.entity.User;
import com.kafka.Producer.Producer.service.CsvReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class CsvUploadController {
    @Autowired
    CsvReaderService csvReaderService;

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a CSV file");
        }

        try {
            List<User> users = csvReaderService.readUsersFromCsv(file);
            // Add logic to send users to Kafka or process them further if needed
        //    users.forEach(System.out::println);  // Printing all users for now
            return ResponseEntity.ok("File uploaded and processed successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }
    }
}
