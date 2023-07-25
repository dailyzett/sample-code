package com.example.dddstart.domain

class ShippingInfo(
    private val receiverName: String,
    private val receiverPhoneNumber: String,
    private val shippingAddress1: String,
    private val shippingAddress2: String,
    private val shippingZipcode: String
) {
}