package org.example.redisexample.domain.entity

import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(name = "members")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "score", nullable = false)
    var score: Int = 0,

    @Column(name = "created_dt", nullable = false)
    val createdDt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_dt")
    private var updatedDt: LocalDateTime? = null
) : Serializable {
    fun updatedScore(inputScore: Int) {
        score += inputScore
        updatedDt = LocalDateTime.now()
    }
}