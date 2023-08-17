package com.example.dddstart.common.event

import org.springframework.stereotype.Component

@Component
class ExternalRefundService : RefundService {
    override fun refund(orderNumber: String) {
        println("ExternalRefundService.refund: $orderNumber")
    }

}
