package org.example.redisexample.controller.message

import org.example.redisexample.domain.req.MessageReq
import org.example.redisexample.service.message.MessageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/message")
class MessageController(
    private val messageService: MessageService,
) {

    @GetMapping("/publish/{channel}")
    fun publishMessage(@PathVariable channel: String, @RequestParam text: String): ResponseEntity<String> {
        val subscribeChannels = messageService.publish(channel, text)
        return ResponseEntity.ok("구독 채널 수: $subscribeChannels")
    }

    @PostMapping("/publish/{channel}")
    fun publishObjectMessage(
        @PathVariable channel: String,
        @RequestBody messageReq: MessageReq
    ): ResponseEntity<String> {
        val subscribeChannels = messageService.publishObject(channel, messageReq)
        return ResponseEntity.ok("구독 채널 수: $subscribeChannels")
    }
}