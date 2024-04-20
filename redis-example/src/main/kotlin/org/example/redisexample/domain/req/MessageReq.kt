package org.example.redisexample.domain.req

data class MessageReq(
    val tokenNumber: String,
    val deviceOs: String,
    val phoneNumber: String,
)