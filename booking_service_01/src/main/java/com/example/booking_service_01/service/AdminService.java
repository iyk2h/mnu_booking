package com.example.booking_service_01.service;

import javax.servlet.http.HttpServletRequest;

import com.example.booking_service_01.dto.AdminDTO;

public interface AdminService {
    AdminDTO findByAid(String aid);
    String checkAdminRole(HttpServletRequest request);
    String checkSessionAid(HttpServletRequest request);
    boolean checkAid(String aid);
    String insertAdminDto(AdminDTO adminDTO);
    void delete(AdminDTO admindDto);
    String update(AdminDTO adminDTO);
    boolean admin_login(String aid ,String pw);
}
