package com.example.booking_service_01.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.booking_service_01.dto.BookingDTO;

public interface BookingService {
    List<BookingDTO> findAll();
    BookingDTO findByBno(Integer bno);
    boolean checkByBno(Integer bno);
    BookingDTO insertBookingDto(BookingDTO bookingDTO);   
    boolean checkBookingTime(Integer fno, LocalDateTime start, LocalDateTime end);
    List<BookingDTO> findByFno(Integer fno);
    List<BookingDTO> findBySid(Integer sid);
    boolean checkByBnoSid(Integer sid, Integer bno);
    BookingDTO findByBnoSid(Integer sid, Integer bno);
    List<BookingDTO> findBookingListByDate(LocalDate date);
    List<BookingDTO> findBookingListByFacilityWhitDate(Integer fno, LocalDate date);
    void deleteBooking (Integer bno);
}
