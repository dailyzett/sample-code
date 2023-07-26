package com.example.ch

import net.jqwik.api.*
import net.jqwik.api.stateful.Action
import net.jqwik.api.stateful.ActionSequence
import org.assertj.core.api.Assertions.*
import java.math.BigDecimal
import java.math.BigDecimal.valueOf
import java.util.*
import java.util.stream.Collectors

class BasketPBTest {
    class AddAction(
        private val product: Product,
        private val qty: Int
    ) : Action<Basket> {
        override fun run(basket: Basket): Basket {
            val currentValue = basket.totalValue

            basket.add(product, qty)

            val newProductValue = product.price.multiply(valueOf(qty.toLong()))
            val newValue = currentValue.add(newProductValue)

            assertThat(basket.totalValue).isEqualByComparingTo(newValue)

            return basket
        }
    }

    class RemoveAction : Action<Basket> {
        override fun run(basket: Basket): Basket {
            val currentValue = basket.totalValue
            val productsInBasket: Set<Product> = basket.products()

            //장바구니가 비어 있다면 이 액션을 건너뛴다. 우리가 jqwik 의 생성 순서를 제어할 수 없기 때문에 이런 동작이 일어날 수 있다.
            if (productsInBasket.isEmpty()) {
                return basket
            }

            val randomProduct = pickRandom(productsInBasket)
            val currentProductQty = basket.quantityOf(randomProduct)
            basket.remove(randomProduct)

            val basketValueWithoutRandomProduct =
                currentValue.subtract(randomProduct.price.multiply(valueOf(currentProductQty!!.toLong())))

            assertThat(basket.totalValue).isEqualByComparingTo(basketValueWithoutRandomProduct)
            return basket
        }

        private fun pickRandom(set: Set<Product>): Product {
            val random = Random()
            val randomNumber = random.nextInt(set.size)

            var randomElement: Product? = null

            //for each element in set
            for ((currentIndex, element) in set.withIndex()) {
                randomElement = element

                if (currentIndex == randomNumber) {
                    return randomElement
                }
            }

            return randomElement!!
        }
    }

    private fun addAction(): Arbitrary<AddAction> {
        val products: Arbitrary<Product> = Arbitraries.oneOf(
            randomProducts
                .stream()
                .map { product: Product -> Arbitraries.of(product) }
                .collect(Collectors.toList()) as Collection<Arbitrary<out Product>>)

        val qtys: Arbitrary<Int> = Arbitraries.integers().between(1, 100)

        return Combinators
            .combine(products, qtys)
            .`as` { product: Product, qty: Int -> AddAction(product, qty) }
    }

    private val randomProducts = listOf(
        Product("TV", BigDecimal("100")),
        Product("Playstation", BigDecimal("150.3")),
        Product("Refrigerator", BigDecimal("180.27")),
        Product("Soda", BigDecimal("2.69"))
    )

    private fun removeAction(): Arbitrary<RemoveAction> {
        return Arbitraries.of(RemoveAction())
    }

    @Provide
    fun addsAndRemove(): Arbitrary<ActionSequence<Basket>> {
        return Arbitraries.sequences(
            Arbitraries.oneOf(
                addAction(),
                removeAction()
            )
        )
    }

    @Property
    fun sequenceOfAddsAndRemove(
        @ForAll("addsAndRemove")
        actions: ActionSequence<Basket>
    ) {
        actions.run(Basket())
    }
}