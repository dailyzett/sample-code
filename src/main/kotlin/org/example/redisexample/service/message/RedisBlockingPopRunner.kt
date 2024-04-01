package org.example.redisexample.service.message

import org.example.redisexample.util.KeyGenerator
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class RedisBlockingPopRunner(
    private val messageService: MessageService,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val key = KeyGenerator.generateQueueKey("a")
        messageService.blockingPop(key)
    }
}