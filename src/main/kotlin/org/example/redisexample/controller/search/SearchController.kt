package org.example.redisexample.controller.search

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/search")
class SearchController {

    @GetMapping
    fun searchKeyword(@RequestParam keyword: String) {
        
    }
}