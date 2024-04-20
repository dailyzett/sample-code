package com.jun.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.*

@Entity
@Table(name = "customer")
class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "customer_id")
    var id: Int = 0,

    val name: String? = null,

    private
    val email: String? = null,

    @Column(name = "mobile_number")
    val mobileNumber: String? = null,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var pwd: String? = null,

    val role: String? = null,

    @Column(name = "create_dt")
    var createDt: Date? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    var authorities: MutableSet<Authority>? = null
)