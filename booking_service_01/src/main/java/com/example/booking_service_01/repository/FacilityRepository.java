package com.example.booking_service_01.repository;

import com.example.booking_service_01.entity.Facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Integer>{
    Facility findByFno(Integer fno);

}
