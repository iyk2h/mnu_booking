package com.example.booking_service_01.service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.example.booking_service_01.dto.BookingDTO;
import com.example.booking_service_01.entity.Booking;
import com.example.booking_service_01.entity.Facility;
import com.example.booking_service_01.entity.Students;
import com.example.booking_service_01.mapper.BookingMapper;
import com.example.booking_service_01.repository.BookingRepository;
import com.example.booking_service_01.repository.FacilityRepository;
import com.example.booking_service_01.repository.StudentsRepository;
import com.example.booking_service_01.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    FacilityRepository facilityRepository;
    @Autowired
    StudentsRepository studentsRepository;


    @Override
    public List<BookingDTO> findAll() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingDTO> bookingDTO = BookingMapper.INSTANCE.booking_To_List_DTO(bookings);
        return bookingDTO;
    }
    @Override
    public BookingDTO findByBno(Integer bno) {
        Booking booking = bookingRepository.findByBno(bno);
        BookingDTO bookingDTO = BookingMapper.INSTANCE.booking_To_DTO(booking);
        return bookingDTO;
    }

    @Override
    public BookingDTO insertBookingDto(BookingDTO bookingDTO) {
        Booking result = BookingMapper.INSTANCE.bookingDto_To_Entity(bookingDTO);
        BookingDTO resultDto;
        try {
            result = bookingRepository.save(result);
            resultDto = BookingMapper.INSTANCE.booking_To_DTO(result);
        }
        catch (Exception e) {
            resultDto = null;
        }
        return resultDto;
    }

    @Override
    public boolean checkByBno(Integer bno) {
        Booking booking = bookingRepository.findByBno(bno);
        if(booking == null) {
            return false;
        }
        return true;
    }

    @Override
    public List<BookingDTO> findByFno(Integer fno) {
        Facility facility = facilityRepository.findByFno(fno);
        List<BookingDTO> dtos = new ArrayList<>();
        List<Booking> entitys = bookingRepository.findByFacility(facility);
        dtos = BookingMapper.INSTANCE.booking_To_List_DTO(entitys);
        return dtos;
    }

    @Override
    public List<BookingDTO> findBySid(Integer sid) {
        Students students = studentsRepository.findBySid(sid);
        List<BookingDTO> dtos = new ArrayList<>();
        List<Booking> entitys = bookingRepository.findByStudents(students);
        dtos = BookingMapper.INSTANCE.booking_To_List_DTO(entitys);
        return dtos;
    }

    @Override
    public List<BookingDTO> findBookingListByDate(LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date, LocalTime.of(0, 0));
        LocalDateTime end = start.plusDays(1);
        List<Booking> entitys = bookingRepository.findAllByStartTimeBetween(start, end);
        
        List<BookingDTO> dtos = BookingMapper.INSTANCE.booking_To_List_DTO(entitys);
        
        return dtos;
    }

    @Override
    public boolean checkByBnoSid(Integer sid, Integer bno) {
        Students students = studentsRepository.findBySid(sid);
        if(bookingRepository.findBySidBno(students, bno) == null){
            return false;
        }
        else
            return true;
    }

    @Override
    public BookingDTO findByBnoSid(Integer sid, Integer bno) {
        Students students = studentsRepository.findBySid(sid);
        Booking bookings = bookingRepository.findBySidBno(students, bno);
        BookingDTO dtos = BookingMapper.INSTANCE.booking_To_DTO(bookings);
        return dtos;
    }

    @Override
    public List<BookingDTO> findBookingListByFacilityWhitDate(Integer fno, LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date, LocalTime.of(0, 0));
        LocalDateTime end = start.plusDays(1);
        Facility facility = facilityRepository.findByFno(fno);
        List<Booking> entitys  = bookingRepository.findAllByFacility(facility, start, end);

        List<BookingDTO> dtos = BookingMapper.INSTANCE.booking_To_List_DTO(entitys);
        return dtos;
    }

    @Override
    public boolean checkBookingTime(Integer fno, LocalDateTime start, LocalDateTime end) {
        Facility facility = facilityRepository.findByFno(fno);
        if (bookingRepository.findAllByFacility(facility, start, end).size() == 0)
            return true;
        else
            return false;
    }

    @Override
    public void deleteBooking (Integer bno) {
        bookingRepository.deleteById(bno);
    }
}
