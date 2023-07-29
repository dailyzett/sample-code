package com.example.dddstart.order.command.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CancelOrderService {

    @Transactional
    fun cancelOrder() {
        //TODO(오더 아이디로 사용자 주문을 찾고 주문을 캔슬한다.)
    }
}