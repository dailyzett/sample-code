package deepboot.deep

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

class HelloApiTest {

    @Test
    fun helloApi() {
        val rest = TestRestTemplate()

        val res: ResponseEntity<String> = rest.getForEntity(
            "http://localhost:9090/app/hello?name={spring}",
            String::class.java,
            arrayOf("Spring")
        )

        assertThat(res.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(res.headers.getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE)
        assertThat(res.body).isEqualTo("*Hello Spring*")
    }
}
