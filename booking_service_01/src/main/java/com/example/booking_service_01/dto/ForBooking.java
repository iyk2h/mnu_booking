package com.example.booking_service_01.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;

@Getter
public class ForBooking {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime selectedTime;
    Integer maxHour;
    Integer snum; 
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "test")
    String sname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "test")
    String smajor;
}
