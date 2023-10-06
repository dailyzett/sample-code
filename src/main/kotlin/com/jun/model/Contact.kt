package com.jun.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.sql.Date

@Entity
@Table(name = "contact_messages")
class Contact(
    @Id
    @Column(name = "contact_id")
    var contactId: String? = null,

    @Column(name = "contact_name")
    var contactName: String? = null,

    @Column(name = "contact_email")
    var contactEmail: String? = null,

    val subject: String? = null,

    val message: String? = null,

    @Column(name = "create_dt")
    var createDt: Date? = null,
)