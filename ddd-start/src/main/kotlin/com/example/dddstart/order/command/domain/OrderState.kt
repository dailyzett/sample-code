package com.example.dddstart.order.command.domain

/**
 * 주문 상태
 * - PAYMENT_WAITING: 결제 대기
 * - PREPARING: 상품 준비
 * - SHIPPED: 배송 중
 * - DELIVERING: 배송 중
 * - DELIVERY_COMPLETE: 배송 완료
 * 결제 대기, 상품 준비 상태일 때는 배송지 정보를 변경할 수 있어야 한다.
 */
enum class OrderState {
    PAYMENT_WAITING {
        override fun isShippingChangeable(): Boolean {
            return true
        }
    },

    PREPARING {
        override fun isShippingChangeable(): Boolean {
            return true
        }
    },

    SHIPPED, DELIVERING, DELIVERY_COMPLETE, CANCELED;

    open fun isShippingChangeable(): Boolean {
        return false
    }
}