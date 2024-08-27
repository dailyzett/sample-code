package org.example.eventsourcing.store.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface CartRepository : JpaRepository<CartJpo, String> {
}