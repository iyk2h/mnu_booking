package com.example.booking_service_01.service.Impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.booking_service_01.dto.AdminDTO;
import com.example.booking_service_01.entity.Admin;
import com.example.booking_service_01.mapper.BookingMapper;
import com.example.booking_service_01.repository.AdminRepository;
import com.example.booking_service_01.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepository adminRepository;

    
    //admin
    @Override
    public AdminDTO findByAid(String aid) {
        Admin admin = adminRepository.findByAid(aid);
        AdminDTO adminDTO = BookingMapper.INSTANCE.admin_To_DTO(admin);
        return adminDTO;
    }

    @Override
    public boolean checkAid(String aid) {
        Admin admin = adminRepository.findByAid(aid);
        if(admin==null) {
            return false;
        }
        return true;
    }
    
    @Override
    public String checkAdminRole(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("id");
        String role = (String) session.getAttribute("role");
        Admin admin = adminRepository.findByAid(id);
        if(admin == null || role != "admin"){
            return id = null;
        }
        else {
            return id;
        }
    }

    @Override
    public String checkSessionAid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("id"); 
        Admin admin = adminRepository.findByAid(id);
        if(admin == null){
            return id = null;
        }
        else {
            return id;
        }
    }

    @Override
    public String insertAdminDto(AdminDTO adminDTO) {
        Admin admin = BookingMapper.INSTANCE.adminDto_To_Entity(adminDTO);
        adminRepository.save(admin);
        return admin.getAid();
    }

    @Override
    public void delete(AdminDTO adminDTO) {
        Admin admin = BookingMapper.INSTANCE.adminDto_To_Entity(adminDTO);
        adminRepository.delete(admin);
    }

    @Override
    public boolean admin_login(String aid ,String pw) {
        Admin admin = adminRepository.findByAid(aid);
        if(admin.getPw().equals(pw)) {
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public String update(AdminDTO adminDTO) {
        Admin admin = BookingMapper.INSTANCE.adminDto_To_Entity(adminDTO);
        adminRepository.save(admin);
        return admin.getAid();
    }

}
