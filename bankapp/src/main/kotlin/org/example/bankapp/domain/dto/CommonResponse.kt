package org.example.bankapp.domain.dto

import org.springframework.http.HttpStatus

data class CommonResponse(
    val status: String = HttpStatus.OK.name,
    val message: String = "성공",
    val data: Any? = null,
)
