package com.example.booking_service_01.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admin_models")
public class Admin {
    @Id
    @Column(length = 20, nullable = false)
    private String aid;

    @Column(length = 20, nullable = false)
    private String pw;
 
    @Column(length = 20, nullable = false)
    private String name;
    
    @Column(length = 13, nullable = false)
    private String phone;
    
    @Column(length = 30, nullable = false)
    private String email;
}
