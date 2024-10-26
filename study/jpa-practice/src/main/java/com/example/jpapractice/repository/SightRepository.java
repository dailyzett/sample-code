package com.example.jpapractice.repository;

import com.example.jpapractice.entity.sight.Sight;
import com.example.jpapractice.entity.sight.SightReview;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SightRepository {

    private final EntityManager em;

    public void save() {
        Sight sight = em.find(Sight.class, "S-01");
        SightReview review = SightReview.builder()
                .id(1L)
                .sightId(sight)
                .grade(5)
                .comment("좋아요")
                .build();
        em.persist(review);
        em.flush();
    }
}
