package com.example.booking_service_01.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.example.booking_service_01.entity.Booking;
import com.example.booking_service_01.entity.Facility;
import com.example.booking_service_01.entity.Students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{
//    List<Booking> findByBnoList(Integer bno);
    
    Booking findByBno(Integer bno);
    List<Booking> findByFacility(Facility facility);
    List<Booking> findByStudents(Students students);

    //booking list from Date
    List<Booking> findAllByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    //booking list from Date with fno
    @Query(value = "select b from Booking AS b where b.facility= ?1 and b.startTime between ?2 and ?3 order by b.startTime")
    List<Booking> findAllByFacility(Facility facility, LocalDateTime startTime, LocalDateTime endTime);
    @Query(value = "select b from Booking AS b where b.students= ?1 and b.bno= ?2")
    Booking findBySidBno(Students students, Integer bno);
}
