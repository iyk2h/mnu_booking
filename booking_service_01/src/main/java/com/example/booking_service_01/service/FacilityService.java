package com.example.booking_service_01.service;

import java.util.List;

import com.example.booking_service_01.dto.FacilityDTO;

public interface FacilityService {
    FacilityDTO findByFno(Integer fno);
    List<FacilityDTO> findAll();
    boolean checkFno(Integer fno);
    Integer insertFacilityDto(FacilityDTO facilityDTO);
    void delete(FacilityDTO facilityDTO);
    Integer update(FacilityDTO facilityDTO);
}
