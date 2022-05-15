package com.example.booking_service_01.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;

@Getter
public class ForFindDate {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;    
}
