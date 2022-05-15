package com.example.booking_service_01.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.booking_service_01.dto.StudentsDTO;

public interface StudentsService {
    List<StudentsDTO> findAll();
    StudentsDTO findBySid(Integer sid);
    boolean checkSid(Integer sid);
    Integer checkSessionSid(HttpServletRequest request);
    Integer insertStudentsDTO(StudentsDTO studentsDTO);
    void delete(StudentsDTO studentsDTO);
    Integer update(StudentsDTO studentsDTO);
    boolean students_login(Integer sid ,String pw); 
}
