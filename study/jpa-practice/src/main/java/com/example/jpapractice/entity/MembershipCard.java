package com.example.jpapractice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "membership_card", catalog = "jpaprac")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MembershipCard {

    @Id
    @Column(name = "card_no")
    private String cardNumber;

    @OneToOne
    @JoinColumn(name = "user_email")
    private User owner;

    private LocalDate expiryDate;

    private Boolean enabled;
}
