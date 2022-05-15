package com.example.booking_service_01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private String aid;
    private String pw;
    private String name;
    private String phone; 
    private String email;
}
