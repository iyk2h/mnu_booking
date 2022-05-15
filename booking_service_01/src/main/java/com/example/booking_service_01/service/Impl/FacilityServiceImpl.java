package com.example.booking_service_01.service.Impl;

import java.util.ArrayList;
import java.util.List;

import com.example.booking_service_01.dto.FacilityDTO;
import com.example.booking_service_01.entity.Booking;
import com.example.booking_service_01.entity.Facility;
import com.example.booking_service_01.mapper.BookingMapper;
import com.example.booking_service_01.repository.BookingRepository;
import com.example.booking_service_01.repository.FacilityRepository;
import com.example.booking_service_01.service.FacilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacilityServiceImpl implements FacilityService{
    @Autowired
    private FacilityRepository facilityRepository;    
    @Autowired
    BookingRepository bookingRepository;
    
    //findByFno
    @Override
    public FacilityDTO findByFno(Integer fno) {
        FacilityDTO facilityDTO;
        Facility facility = facilityRepository.findByFno(fno);
        facilityDTO = BookingMapper.INSTANCE.facility_To_DTO(facility);
        return facilityDTO;
    }

    @Override
    public List<FacilityDTO> findAll() {
        List<FacilityDTO> dtos = new ArrayList<>();
        List<Facility> entitys = facilityRepository.findAll();
        dtos = BookingMapper.INSTANCE.facility_To_List_DTO(entitys);
        return dtos;
    }
    
    @Override
    public boolean checkFno(Integer fno) {
        Facility facility = facilityRepository.findByFno(fno);
        if(facility==null) {
            return false;
        }
        return true;
    }

    @Override
    public Integer insertFacilityDto(FacilityDTO facilityDTO) {
        Facility facility = BookingMapper.INSTANCE.facilityDTO_To_Entity(facilityDTO);
        facilityRepository.save(facility);
        return facility.getFno();
    }

    @Override
    public void delete(FacilityDTO facilityDTO) {
        Facility facility = BookingMapper.INSTANCE.facilityDTO_To_Entity(facilityDTO);
        List<Booking> bookings = bookingRepository.findByFacility(facility);
        bookingRepository.deleteAll(bookings);
        facilityRepository.delete(facility);
    }

    @Override
    public Integer update(FacilityDTO facilityDTO) {
        Facility facility = BookingMapper.INSTANCE.facilityDTO_To_Entity(facilityDTO);
        facilityRepository.save(facility);
        return facility.getFno();
    }
}
