package com.jun.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name = "authorities")
class Authority(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    var id: Long = 0,
    
    var name: String = "",

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null
) {

}