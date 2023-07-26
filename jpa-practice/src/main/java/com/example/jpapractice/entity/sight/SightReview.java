package com.example.jpapractice.entity.sight;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SightReview {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sight_id")
    private Sight sightId;

    private int grade;

    private String comment;
}
