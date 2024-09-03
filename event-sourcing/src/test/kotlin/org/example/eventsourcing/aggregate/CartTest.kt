package org.example.eventsourcing.aggregate

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.example.eventsourcing.command.AddItem
import org.example.eventsourcing.command.RemoveItem

class CartTest : BehaviorSpec() {
    private val cartId = "1234"

    init {
        Given("AddItem 이벤트가 주어질 때") {
            val cart = Cart(cartId)
            val addItem = AddItem(cartId, "PD002", "iPad mini", 65000)

            When("카트에 아이템을 추가하면") {
                cart.addItem(addItem)

                Then("카트에 담긴 아이템은 두 개 여야 한다.") {
                    cart.items.size shouldBe 1
                }
            }

            When("카트에 아이템을 삭제하면") {
                val removeItem = RemoveItem(cartId, "PD002")
                cart.removeItem(removeItem)
                cart.items.size shouldBe 0
            }
        }
    }
}