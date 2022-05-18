package com.example.booking_service_01.controller;
import java.util.List;

import javax.websocket.server.PathParam;

import com.example.booking_service_01.dto.BookingDTO;
import com.example.booking_service_01.dto.SnumDTO;
import com.example.booking_service_01.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses({
    @ApiResponse(code = 200, message = "get 성공"),
    @ApiResponse(code = 201, message = "post 성공"),
    @ApiResponse(code = 202, message = "입력값을 확인하세요"),
    @ApiResponse(code = 204, message = "반환 값 없는 상태"),
    @ApiResponse(code = 400, message = "잘못된 요청 구문"),
    @ApiResponse(code = 401, message = "로그인 필요한 상태"),
    @ApiResponse(code = 404, message = "경로 혹은 입력값이 잘못된 상태"),
    @ApiResponse(code = 500, message = "서버에러")
})
@RestController
@RequestMapping(path = "/manage")
public class PubManageController {
    @Autowired
    BookingService bookingService;


    //------------ manage booking --------------//


    // 예약 삭제
    @DeleteMapping(path="booking/{bno}", produces = "application/json")
    public ResponseEntity<?> deleteBookingByBno(@PathVariable("bno") Integer bno) {
        if(!bookingService.checkByBno(bno)) {
            return new ResponseEntity<>("bno can not found", HttpStatus.ACCEPTED);
        }
        else {
            bookingService.deleteBooking(bno);
            return new ResponseEntity<>("삭제되었습니다.",HttpStatus.NO_CONTENT);
        }
    }

    // 시설별 예약 조회
    @GetMapping(path = "booking/{fno}", produces = "application/json")
    public ResponseEntity<?> getBookingListByFno(@PathVariable("fno") Integer fno) {
        if(bookingService.findByFno(fno).isEmpty()) {
            return new ResponseEntity<>("예약건이 없습니다.",HttpStatus.ACCEPTED); 
        }
        else {
            List<BookingDTO> bookingDTOs = bookingService.findByFno(fno);
            return new ResponseEntity<>(bookingDTOs,HttpStatus.OK);
        }
    }
    @PostMapping(path = "booking/snum", produces = "application/json")
    public ResponseEntity<?> getBookingListBySnum(@RequestBody SnumDTO snumDTO)  {
        Integer snum = snumDTO.getSnum();
        if(bookingService.findBySnum(snum).isEmpty()) {
            return new ResponseEntity<>("예약건이 없습니다.",HttpStatus.ACCEPTED); 
        }
        else {
            List<BookingDTO> bookingDTOs = bookingService.findBySnum(snum);
            return new ResponseEntity<>(bookingDTOs,HttpStatus.OK);
        }
    }
}
