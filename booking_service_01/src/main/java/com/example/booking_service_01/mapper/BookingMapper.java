package com.example.booking_service_01.mapper;

import java.util.List;

import com.example.booking_service_01.dto.AdminDTO;
import com.example.booking_service_01.dto.BookingDTO;
import com.example.booking_service_01.dto.FacilityDTO;
import com.example.booking_service_01.dto.StudentsDTO;
import com.example.booking_service_01.entity.Admin;
import com.example.booking_service_01.entity.Booking;
import com.example.booking_service_01.entity.Facility;
import com.example.booking_service_01.entity.Students;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);
    AdminDTO admin_To_DTO(Admin admin);
    Admin adminDto_To_Entity(AdminDTO adminDTO);

    @Mapping(target = "sid", source = "students.sid") 
    @Mapping(target = "fno", source = "facility.fno") 
    BookingDTO booking_To_DTO(Booking booking);
    @Mapping(target = "students.sid", source = "sid")
    @Mapping(target = "facility.fno", source = "fno")
    Booking bookingDto_To_Entity(BookingDTO bookigdDto); 
    List<BookingDTO> booking_To_List_DTO(List<Booking> bookings);

    FacilityDTO facility_To_DTO(Facility facility);
    List<FacilityDTO> facility_To_List_DTO(List<Facility> facilities);
    Facility facilityDTO_To_Entity(FacilityDTO facilityDTO);
    
    StudentsDTO students_To_DTO(Students students);
    Students studentsDTO_To_Entity(StudentsDTO studentsDTO);
    List<StudentsDTO> students_To_List_DTO(List<Students> students);
}
