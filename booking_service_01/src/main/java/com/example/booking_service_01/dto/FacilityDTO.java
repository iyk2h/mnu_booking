package com.example.booking_service_01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDTO {
    private Integer fno;
    private String name;
    private String place;
    private String placeUrl;
    private Integer maxHour; 
}
