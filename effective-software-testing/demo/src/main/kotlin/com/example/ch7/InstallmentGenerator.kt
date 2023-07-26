package com.example.ch7

import java.time.LocalDate

/**
 * 할부 시스템
 */
class InstallmentGenerator(
    private val repository: InstallmentRepository
) {
    fun generateInstallments(cart: ShoppingCart, numberOfInstallments: Int) {
        var nextInstallmentDueDate = LocalDate.now()

        val amountPerInstallment: Double = cart.value / numberOfInstallments

        for (i in 1..numberOfInstallments) {
            nextInstallmentDueDate = nextInstallmentDueDate.plusMonths(1)

            val newInstallment = Installment(nextInstallmentDueDate, amountPerInstallment)
            repository.persist(newInstallment)
        }
    }

    fun generateInstallments2(cart: ShoppingCart, numberOfInstallments: Int): List<Installment> {
        val generateInstallments = mutableListOf<Installment>()
        var nextInstallmentDueDate = LocalDate.now()

        val amountPerInstallment: Double = cart.value / numberOfInstallments

        for (i in 1..numberOfInstallments) {
            nextInstallmentDueDate = nextInstallmentDueDate.plusMonths(1)

            val newInstallment = Installment(nextInstallmentDueDate, amountPerInstallment)
            repository.persist(newInstallment)

            generateInstallments.add(newInstallment)
        }

        return generateInstallments
    }
}