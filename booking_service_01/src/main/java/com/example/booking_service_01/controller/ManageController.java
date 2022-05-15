package com.example.booking_service_01.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.booking_service_01.dto.BookingDTO;
import com.example.booking_service_01.dto.FacilityDTO;
import com.example.booking_service_01.service.BookingService;
import com.example.booking_service_01.service.FacilityService;
import com.example.booking_service_01.service.StudentsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(path = "/manage")
public class ManageController {
    @Autowired
    StudentsService studentsService;
    @Autowired
    FacilityService facilityService;
    @Autowired
    BookingService bookingService;
    //-------------- manage students -------------//
    //사용자 list
    @GetMapping(path="/students", produces = "application/json")
    public ResponseEntity<?> getStudentsList(){
        return new ResponseEntity<>(studentsService.findAll(), HttpStatus.OK);
    }
    //회원 정보
    @GetMapping(path="/students/{sid}", produces = "application/json")
    public ResponseEntity<?> getStudentBySid(@PathVariable("sid") Integer sid) {
        if(!studentsService.checkSid(sid)) {
            return new ResponseEntity<>("sid can not found", HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(studentsService.findBySid(sid), HttpStatus.OK);
        }
    }
    //회원 탈퇴
    @DeleteMapping(path="/students/{sid}", produces = "application/json")
    public ResponseEntity<?> deleteStudent(@PathVariable("sid") Integer sid) {
        if(!studentsService.checkSid(sid))
            return new ResponseEntity<>("sid can not found", HttpStatus.NOT_FOUND);
        else {
            studentsService.delete(studentsService.findBySid(sid));
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    //------------ manage booking --------------//
    //bookinglist
    @GetMapping(path = "/booking", produces = "application/json")
    public ResponseEntity<?> getBookingList(HttpServletRequest request) {
        return new ResponseEntity<>(bookingService.findAll(),HttpStatus.OK);
    }

    //예약 정보
    @GetMapping(path = "/booking/{bno}", produces = "application/json")
    public ResponseEntity<?> getBookingByBno(@PathVariable("bno") Integer bno ) { 
        if(!bookingService.checkByBno(bno)) {
            return new ResponseEntity<>("bno can not found", HttpStatus.NOT_FOUND);
        }
        else {
            BookingDTO bookingDTO = bookingService.findByBno(bno);
            return new ResponseEntity<>(bookingDTO, HttpStatus.OK);   
        }
    }
    // 예약 삭제
    @DeleteMapping(path="booking/{bno}", produces = "application/json")
    public ResponseEntity<?> deleteBookingByBno(@PathVariable("bno") Integer bno) {
        if(!bookingService.checkByBno(bno)) {
            return new ResponseEntity<>("bno can not found", HttpStatus.NOT_FOUND);
        }
        else {
            bookingService.deleteBooking(bno);
            return new ResponseEntity<>("삭제되었습니다.",HttpStatus.NO_CONTENT);
        }
    }
    //----------- manage facility -------------//
    //insert
    @PostMapping(path = "/facility/join", produces = "application/json")
    public ResponseEntity<?> insertFacility(@RequestBody FacilityDTO facilityDTO) {
        if(facilityDTO!=null && facilityDTO.getFno()==null) {
            return new ResponseEntity<>(facilityService.findByFno(facilityService.insertFacilityDto(facilityDTO)), HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("잘못 입력되었습니다.",HttpStatus.NOT_FOUND);
        }
    }

    //select all
    @GetMapping(path="/facility", produces = "application/json")
    public ResponseEntity<?> getAllFacility() {
        return new ResponseEntity<>(facilityService.findAll(), HttpStatus.OK);
    }

    //Select by fno
    @GetMapping(path="/facility/{fno}", produces = "application/json")
    public ResponseEntity<?> getByFno(@PathVariable("fno") Integer fno) {
        if(!facilityService.checkFno(fno)) {
            return new ResponseEntity<>("fno can not found", HttpStatus.NOT_FOUND);
        }
        else {
            FacilityDTO facilityDTO = facilityService.findByFno(fno);
            return new ResponseEntity<>(facilityDTO, HttpStatus.OK);
        }
    }

    //Update   
    @PutMapping(path = "/facility/{fno}", produces = "application/json")
    public ResponseEntity<?> updateFacility(@PathVariable("fno") Integer fno, @RequestBody FacilityDTO facilityDTO) {
        FacilityDTO beforeDTO = facilityService.findByFno(fno);
        if(facilityDTO != null){
            Integer u_fno = facilityDTO.getFno()!=null?fno:beforeDTO.getFno();
            String u_place = facilityDTO.getPlace()!=null?facilityDTO.getPlace():beforeDTO.getPlace();
            String u_placeUrl = facilityDTO.getPlaceUrl()!=null?facilityDTO.getPlaceUrl():beforeDTO.getPlaceUrl();
            Integer u_maxHour = facilityDTO.getMaxHour()!=null?facilityDTO.getMaxHour():beforeDTO.getMaxHour();
            String u_name = facilityDTO.getName()!=null?facilityDTO.getName():beforeDTO.getName();

            FacilityDTO updateDTO= FacilityDTO.builder()
                .fno(u_fno)
                .place(u_place)
                .placeUrl(u_placeUrl)
                .name(u_name)
                .maxHour(u_maxHour)
                .build();
            
            return new ResponseEntity<>(facilityService.findByFno(facilityService.update(updateDTO)), HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>("Update fail", HttpStatus.BAD_REQUEST);
    }

    //Delete
    @DeleteMapping(path="/facility/{fno}", produces = "application/json")
    public ResponseEntity<?> deleteFacility(@PathVariable("fno") Integer fno) {
        if(!facilityService.checkFno(fno))
            return new ResponseEntity<>("Facility Number can not found", HttpStatus.NOT_FOUND);
        else {
            FacilityDTO facilityDTO = facilityService.findByFno(fno);
            facilityService.delete(facilityDTO);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }
}
