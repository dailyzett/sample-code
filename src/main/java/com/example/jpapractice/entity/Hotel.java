package com.example.jpapractice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "hotel_info", catalog = "jpaprac")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm")
    private String name;

    private int year;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime lastModified;
}
