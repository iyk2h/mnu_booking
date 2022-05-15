package com.example.booking_service_01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication
public class BookingService01Application {

	public static void main(String[] args) {
		SpringApplication.run(BookingService01Application.class, args);
	}

}
