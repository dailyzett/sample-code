package com.example.ch7

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class InstallmentGeneratorTest(
    //1. 저장소 모의 객체를 생성합니다.
    @Mock private val repository: InstallmentRepository
) {
    @Test
    fun `installment captor test`() {
        val generator = InstallmentGenerator(repository)

        //2. 테스트 대상 클래스의 인스턴스를 생성하고 의존성으로 모의 객체를 전달합니다.
        val cart = ShoppingCart(100.0)

        //3. 테스트 대상 메서드를 호출합니다. 이 함수는 void 를 리턴합니다.
        generator.generateInstallments(cart, 10)

        //4. 인자를 캡처하기 위해 ArgumentCaptor 를 선언합니다.
        val captor: ArgumentCaptor<Installment> = ArgumentCaptor.forClass(Installment::class.java)

        //5. 포획기를 이용해서 저장소에 전달된 모든 할부 객체를 얻습니다.
        verify(repository, times(10)).persist(capture(captor))

        val allInstallments: List<Installment> = captor.allValues

        //6. 할부가 올바른지 확인합니다. 모든 할부는 값이 10이여야 합니다.
        assertThat(allInstallments)
            .hasSize(10)
            .allMatch { it.value == 10.0 }

        //7. 할부가 한 달 간격으로 이루어져 있는지 확인합니다.
        for (month in 1..10) {
            val dueDate = LocalDate.now().plusMonths(month.toLong())
            assertThat(allInstallments)
                .anyMatch { i -> i.date == dueDate }
        }
    }

    @Test
    fun `installment no captor test`() {
        //given
        val generator = InstallmentGenerator(repository)
        val cart = ShoppingCart(100.0)

        //when
        val allInstallments = generator.generateInstallments2(cart, 10)

        //then
        assertThat(allInstallments)
            .hasSize(10)
            .allMatch { it.value == 10.0 }

        for (month in 1..10) {
            val dueDate = LocalDate.now().plusMonths(month.toLong())
            assertThat(allInstallments)
                .anyMatch { i -> i.date == dueDate }
        }
    }
}

// 코틀린은 Nullable 한 타입을 지원하지 않습니다. 따라서 capture 메서드에 대해 재선언이 필요합니다.
fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()