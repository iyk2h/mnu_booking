package com.example.booking_service_01.controller;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.booking_service_01.dto.AdminDTO;
import com.example.booking_service_01.dto.ForChangPW;
import com.example.booking_service_01.dto.ForUpdateAdmin;
import com.example.booking_service_01.dto.LoginAdminDTO;
import com.example.booking_service_01.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses({
    @ApiResponse(code = 200, message = "get 성공"),
    @ApiResponse(code = 201, message = "post 성공"),
    @ApiResponse(code = 204, message = "반환 값 없는 상태"),
    @ApiResponse(code = 400, message = "잘못된 요청 구문"),
    @ApiResponse(code = 401, message = "로그인 필요한 상태"),
    @ApiResponse(code = 404, message = "경로 혹은 입력값이 잘못된 상태"),
    @ApiResponse(code = 500, message = "서버에러")
})
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    
    // 아이디 중복 확인
    @PostMapping(path = "/check", produces = "application/json")
    public ResponseEntity<?> sidCheck(@RequestBody LoginAdminDTO loginDTO) {
        if (adminService.checkAid(loginDTO.getAid()))
            return new ResponseEntity<>("id가 이미 존재합니다.",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //singup
    @PostMapping(path = "/singup", produces = "application/json")
    public ResponseEntity<?> singupAdmin(@RequestBody AdminDTO adminDTO) {
        if(adminDTO.getAid()!=null) {
            if (adminService.checkAid(adminDTO.getAid()))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(adminService.findByAid(adminService.insertAdminDto(adminDTO)), HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //mypage
    @GetMapping(path="", produces = "application/json")
    public ResponseEntity<?> getAid(HttpServletRequest request) {
        String aid = adminService.checkAdminRole(request);
        if(!adminService.checkAid(aid)) {
            return new ResponseEntity<>("aid can not found", HttpStatus.NOT_FOUND);
        }
        else {
            AdminDTO adminDTO = adminService.findByAid(aid);
            return new ResponseEntity<>(adminDTO, HttpStatus.OK);
        }
    }

    //Update   
    @PutMapping(path = "", produces = "application/json")
    public ResponseEntity<?> updateAdmin(@RequestBody ForUpdateAdmin newDTO, HttpServletRequest request) {
        String aid = adminService.checkAdminRole(request);
        AdminDTO beforeDTO = adminService.findByAid(aid);
        if(beforeDTO != null){
            if(!adminService.admin_login(aid, newDTO.getPw())){
                return new ResponseEntity<>("비밀번호가 잘못 되었습니다.", HttpStatus.NOT_FOUND); 
            }
            String u_phone = newDTO.getPhone()!=null?newDTO.getPhone():beforeDTO.getPhone();
            String u_name = newDTO.getName()!=null?newDTO.getName():beforeDTO.getName();
            String u_email = newDTO.getEmail()!=null?newDTO.getEmail():beforeDTO.getEmail();

            AdminDTO updateDTO= AdminDTO.builder()
                .aid(aid)
                .pw(beforeDTO.getPw())
                .phone(u_phone)
                .name(u_name)
                .email(u_email)
                .build();
            
            return new ResponseEntity<>(adminService.findByAid(adminService.update(updateDTO)), HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>("Update fail", HttpStatus.BAD_REQUEST);
    }

    //Delete
    @DeleteMapping(path="", produces = "application/json")
    public ResponseEntity<?> deleteAdmin(HttpServletRequest request) {
        String aid = adminService.checkAdminRole(request);
        if(!adminService.checkAid(aid))
            return new ResponseEntity<>("Admin ID can not found", HttpStatus.NOT_FOUND);
        else {
            AdminDTO admindDto = adminService.findByAid(aid);
            adminService.delete(admindDto);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    // login 로그인
    @PostMapping(path = "/login", produces = "application/json")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginAdminDTO loginDTO, HttpServletRequest request) throws URISyntaxException {
        if(!adminService.checkAid(loginDTO.getAid())) {
            return new ResponseEntity<>("aid can not found", HttpStatus.NOT_FOUND);
        }
        else
            if(adminService.admin_login(loginDTO.getAid(), loginDTO.getPw()) == true){
                HttpSession session = request.getSession();
                session.setAttribute("id", loginDTO.getAid());
                session.setAttribute("role", "admin");

            return new ResponseEntity<>("성공 ",HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>("login fail", HttpStatus.NOT_FOUND);
            }
    }  
    //logout
    @PostMapping(path = "/logout", produces = "application/json")
    public ResponseEntity<?> logoutAdmin(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //update Password
    @PutMapping(path = "password",produces = "application/json")
    public ResponseEntity<?> updateAdminPW(@RequestBody ForChangPW forChangPW, HttpServletRequest request) {
        String aid = adminService.checkSessionAid(request);
        AdminDTO adminDTO = adminService.findByAid(aid);
        if(adminService.admin_login(aid, forChangPW.getOldPw())){ 
            AdminDTO updateDTO = AdminDTO.builder()
                .aid(aid)
                .pw(forChangPW.getNewPw())
                .phone(adminDTO.getPhone())
                .name(adminDTO.getName())
                .email(adminDTO.getEmail())
                .build();
            adminService.update(updateDTO);
            return new ResponseEntity<>("성공",HttpStatus.CREATED);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }


}
