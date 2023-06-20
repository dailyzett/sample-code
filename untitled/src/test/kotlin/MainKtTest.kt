import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MainKtTest {
    @Test
    fun simpleCase() {
        //given
        val str = "abcd"
        val open = "a"
        val close = "d"

        //when
        val result = subStringBetween(str, open, close)

        //then
        Assertions.assertEquals(listOf("bc"), result)
    }
}