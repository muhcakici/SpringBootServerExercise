package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student halil = new Student(
                    "Halil","halil@gmail.com",
                    LocalDate.of(2000, APRIL,10));
            Student musa = new Student(
                    "Musa","musa@gmail.com",
                    LocalDate.of(1990, JANUARY,10));
            Student ali = new Student(
                    "Ali","ali@gmail.com",
                    LocalDate.of(1990, JANUARY,10));

            repository.saveAll(List.of(halil,musa,ali));
        };
    }
}
