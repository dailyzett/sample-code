package com.jun.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.sql.Date

@Entity
@Table(name = "notice_details")
class Notice(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "notice_id")
    var noticeId: Int = 0,

    @Column(name = "notice_summary")
    var noticeSummary: String? = null,

    @Column(name = "notice_details")
    var noticeDetails: String? = null,

    @Column(name = "notic_beg_dt")
    var noticBegDt: Date? = null,

    @Column(name = "notic_end_dt")
    var noticEndDt: Date? = null,

    @Column(name = "create_dt")
    var createDt: Date? = null,

    @Column(name = "update_dt")
    var updateDt: Date? = null,
)