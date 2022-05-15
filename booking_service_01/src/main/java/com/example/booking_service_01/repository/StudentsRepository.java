package com.example.booking_service_01.repository;

import java.util.List;

import com.example.booking_service_01.entity.Students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface StudentsRepository extends JpaRepository<Students, Integer>{
    Students findBySid(Integer sid);
    List<Students> findListBySid(Integer sid);
}
