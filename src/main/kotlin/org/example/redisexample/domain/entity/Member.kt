package org.example.redisexample.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "members")
class Member (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0L,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "score", nullable = false)
    val score: Int = 0,

    @Column(name = "created_dt", nullable = false)
    val createdDt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_dt")
    val updatedDt: LocalDateTime? = null
)