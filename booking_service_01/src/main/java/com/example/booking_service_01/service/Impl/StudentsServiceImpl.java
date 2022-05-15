package com.example.booking_service_01.service.Impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.booking_service_01.dto.StudentsDTO;
import com.example.booking_service_01.entity.Booking;
import com.example.booking_service_01.entity.Students;
import com.example.booking_service_01.mapper.BookingMapper;
import com.example.booking_service_01.repository.BookingRepository;
import com.example.booking_service_01.repository.StudentsRepository;
import com.example.booking_service_01.service.StudentsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentsServiceImpl implements StudentsService{
    @Autowired
    StudentsRepository studentsRepository;
    @Autowired
    BookingRepository bookingRepository;

    @Override
    public List<StudentsDTO> findAll() {
        List<Students> students = studentsRepository.findAll();
        List<StudentsDTO> studentsDTOs = BookingMapper.INSTANCE.students_To_List_DTO(students);
        return studentsDTOs;
    }

    @Override
    public StudentsDTO findBySid(Integer sid) {
        Students students = studentsRepository.findBySid(sid);
        StudentsDTO studentsDTO = BookingMapper.INSTANCE.students_To_DTO(students);
        return studentsDTO;
    }

    @Override
    public boolean checkSid(Integer sid) {
        Students students = studentsRepository.findBySid(sid);
        if(students==null) {
            return false;
        }
        return true;
    }

    @Override
    public Integer insertStudentsDTO(StudentsDTO studentsDTO) {
        Students students = BookingMapper.INSTANCE.studentsDTO_To_Entity(studentsDTO);
        studentsRepository.save(students);
        return students.getSid();
    }

    @Override
    public void delete(StudentsDTO studentsDTO) {
        Students students = BookingMapper.INSTANCE.studentsDTO_To_Entity(studentsDTO);
        List<Booking> bookings = bookingRepository.findByStudents(students);
        bookingRepository.deleteAll(bookings);
        studentsRepository.delete(students);
    }

    @Override
    public boolean students_login(Integer sid ,String pw) {
        Students students = studentsRepository.findBySid(sid);
        if(students.getPw().equals(pw)) {
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public Integer update(StudentsDTO studentsDTO) {
        Students students = BookingMapper.INSTANCE.studentsDTO_To_Entity(studentsDTO);
        studentsRepository.save(students);
        return students.getSid();
    }

    @Override
    public Integer checkSessionSid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer sid = (Integer) session.getAttribute("id");
        if(sid == null)
            sid = null;
        return sid;
    }


    
}
