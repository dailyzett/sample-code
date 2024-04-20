package org.example.redisexample.util

import org.assertj.core.api.Assertions.assertThat
import org.example.redisexample.domain.req.MessageReq
import org.junit.jupiter.api.Test

class TransformUtilTest {

    @Test
    fun givenMessageRequest_whenConvertedToMap_thenCorrectValuesAreContainedInMap() {
        val messageReq = MessageReq("hello", "Android", "012-0000-0000")
        val toMap = messageReq.toMap()

        assertThat(toMap["tokenNumber"]).isEqualTo("hello")
        assertThat(toMap["deviceOs"]).isEqualTo("Android")
        assertThat(toMap["phoneNumber"]).isEqualTo("012-0000-0000")
    }
}