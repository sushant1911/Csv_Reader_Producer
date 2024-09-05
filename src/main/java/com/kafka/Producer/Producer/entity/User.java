package com.kafka.Producer.Producer.entity;


import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import lombok.NonNull;

@Data
@NoArgsConstructor // Lombok will generate the no-argument constructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NonNull
    @CsvBindByName(column = "first_name")
    private String first_name;

    @NonNull
    @CsvBindByName(column = "last_name")
    private String last_name;

    @Email
    @CsvBindByName(column = "email")
    private String email;

    @CsvBindByName(column = "gender")
    private String gender;

    @CsvBindByName(column = "city")
    private String city;

    @CsvBindByName(column = "Mobile Number") // Ensure this matches the column name in your CSV file
    private String mobile_number; // Changed to String to accommodate various formats
}
