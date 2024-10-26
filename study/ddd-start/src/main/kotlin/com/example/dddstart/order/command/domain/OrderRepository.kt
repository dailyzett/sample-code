package com.example.dddstart.order.command.domain

import org.springframework.data.repository.Repository

interface OrderRepository : Repository<Order, OrderNo> {

    fun findById(id: OrderNo): Order?

    fun save(order: Order)
}