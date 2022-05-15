package com.example.booking_service_01.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.booking_service_01.dto.BookingDTO;
import com.example.booking_service_01.dto.ForBooking;
import com.example.booking_service_01.dto.ForFindDate;
import com.example.booking_service_01.service.BookingService;
import com.example.booking_service_01.service.FacilityService;
import com.example.booking_service_01.service.StudentsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ApiResponse(code = 204, message = "반환 값 없는 상태"),
    @ApiResponse(code = 400, message = "잘못된 요청 구문"),
    @ApiResponse(code = 401, message = "로그인 필요한 상태"),
    @ApiResponse(code = 404, message = "경로 혹은 입력값이 잘못된 상태"),
    @ApiResponse(code = 500, message = "서버에러")
})
@RestController
@RequestMapping(path="/booking")
public class BookingController {
    @Autowired
    FacilityService facilityService;
    @Autowired
    BookingService bookingService;
    @Autowired
    StudentsService studentsService;


    //Booking facility, student
    // @PostMapping(path="/{fno}", produces = "application/json")
    // public ResponseEntity<?> bookingFacilityStudent(@PathVariable("fno") Integer fno, @RequestBody ForBooking bookingDTO,HttpServletRequest request)  {
    //     Integer time = bookingDTO.getSelectedTime().getHour();
    //     Integer seletedHour = bookingDTO.getMaxHour();
    //     Integer sid = studentsService.checkSessionSid(request);
    //     if(!facilityService.checkFno(fno)) {
    //         return new ResponseEntity<>("시설을 확인해 주세요.", HttpStatus.NOT_FOUND);
    //     }
    //     else {
    //         if(time>=24 || time<0) {
    //             return new ResponseEntity<>("예약 시간을 확인해 주세요.", HttpStatus.NOT_FOUND);  
    //         }
    //         LocalDateTime start = LocalDateTime.of(bookingDTO.getDate(), bookingDTO.getSelectedTime());
    //         LocalDateTime end  = start.plusHours(seletedHour).minusSeconds(1);
    //         if(bookingService.checkBookingTime(fno, start, end)) {
    //             for( Integer i = 0; i <bookingDTO.getMaxHour(); i++) {
    //             BookingDTO newBookingDTO = BookingDTO.builder()
    //                 .sid(sid)
    //                 .fno(fno)
    //                 .startTime(start.plusHours(i))
    //                 .endTime(start.plusHours(i+1).minusSeconds(1))
    //                 .maxHour(bookingDTO.getMaxHour())
    //                 .build();
                    
    //                 bookingService.insertBookingDto(newBookingDTO);
    //             }
    //             return new ResponseEntity<>(bookingDTO, HttpStatus.CREATED);
    //         }
    //         else
    //             return new ResponseEntity<>("예약 시간을 확인해 주세요.", HttpStatus.NOT_FOUND);  
    //     }
    // }

    //----------- public -------------//

    // 날자에 예약된 목록 조회
    @PostMapping(path="/{fno}/date", produces = "application/json")
    public ResponseEntity<?> bookingByDate(@PathVariable("fno") Integer fno, @RequestBody ForFindDate bookingDTO) {
        LocalDate date =bookingDTO.getDate();
        List<BookingDTO> bookingDTOs = bookingService.findBookingListByFacilityWhitDate(fno, date);
        return new ResponseEntity<>(bookingDTOs, HttpStatus.CREATED);
    }

    //Booking facility, student
    @PostMapping(path="/{fno}/pub", produces = "application/json")
    public ResponseEntity<?> pub(@PathVariable("fno") Integer fno, @RequestBody ForBooking bookingDTO)  {
        Integer time = bookingDTO.getSelectedTime().getHour();
        Integer seletedHour = bookingDTO.getMaxHour();
        if(!facilityService.checkFno(fno)) {
            return new ResponseEntity<>("시설을 확인해 주세요.", HttpStatus.ACCEPTED);
        }
        else {
            if(time>=24 || time<0) {
                return new ResponseEntity<>("예약 시간을 확인해 주세요.", HttpStatus.ACCEPTED);  
            }
            LocalDateTime start = LocalDateTime.of(bookingDTO.getDate(), bookingDTO.getSelectedTime());
            LocalDateTime end  = start.plusHours(seletedHour).minusSeconds(1);
            if(bookingDTO.getSnum() == null) {
                return new ResponseEntity<>("학번을 일력해 주세요.", HttpStatus.ACCEPTED); 
            }
            if(bookingDTO.getSname() == null) {
                return new ResponseEntity<>("이름을 일력해 주세요.", HttpStatus.ACCEPTED); 
            }
            if(bookingDTO.getSmajor() == null) {
                return new ResponseEntity<>("학과를 일력해 주세요.", HttpStatus.ACCEPTED); 
            }
            if(bookingService.checkBookingTime(fno, start, end)) {
                for( Integer i = 0; i <bookingDTO.getMaxHour(); i++) {
                BookingDTO newBookingDTO = BookingDTO.builder()
                    .sid(1)
                    .fno(fno)
                    .sname(bookingDTO.getSname())
                    .snum(bookingDTO.getSnum())
                    .smajor(bookingDTO.getSmajor())
                    .startTime(start.plusHours(i))
                    .endTime(start.plusHours(i+1).minusSeconds(1))
                    .maxHour(bookingDTO.getMaxHour())
                    .build();
                    
                    bookingService.insertBookingDto(newBookingDTO);
                }
                return new ResponseEntity<>(bookingDTO, HttpStatus.CREATED);
            }
            else
                return new ResponseEntity<>("예약 시간을 확인해 주세요.", HttpStatus.ACCEPTED);  
        }
    }
}