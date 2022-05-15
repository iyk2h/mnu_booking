package com.example.booking_service_01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentsDTO {
    private Integer sid;
    private String pw;
    private String name;
    private String phone;
}
