package com.example.booking_service_01.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "booking_models")
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer bno;

    @ManyToOne
    @JoinColumn(name = "fno")
    private Facility facility;

    @ManyToOne
    @JoinColumn(name = "sid")
    private Students students;

    @Column(name="s_num",nullable = false)
    private Integer snum;

    @Column(name="s_name",nullable = false)
    private String sname;

    @Column(name="s_major",nullable = false)
    private String smajor;

    @Column(name="start_time", nullable = false)
    private LocalDateTime startTime;
 
    @Column(name="end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name="max_hour", nullable = false)
    private Integer maxHour;
}
