package com.example.booking_service_01.repository;

import com.example.booking_service_01.entity.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String>{
    Admin findByAid(String aid);

    // @Modifying
    // @Transactional
    // @Query("delete from admin_models a where a.aid in :aid")
    // void deleteByAid(@Param("aid") Integer aid);
    // Admin delete()
}
