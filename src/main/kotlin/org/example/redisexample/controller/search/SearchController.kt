package org.example.redisexample.controller.search

import org.example.redisexample.service.search.SearchService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/search")
class SearchController(
    private val searchService: SearchService,
) {

    @GetMapping
    fun searchKeyword(@RequestParam keyword: String): ResponseEntity<String> {
        val searchResults = searchService.search(keyword)
        return ResponseEntity.ok(searchResults)
    }

    @GetMapping("/recently")
    fun keywordDetails(): ResponseEntity<List<String>> {
        val latestKeywords = searchService.findKeywordRecently()
        return ResponseEntity.ok(latestKeywords)
    }
}