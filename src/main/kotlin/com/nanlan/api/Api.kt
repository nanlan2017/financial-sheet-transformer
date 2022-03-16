package com.nanlan.api

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
class Api {
    @GetMapping
    fun hello(): String {
        return "hello"
    }

    @GetMapping("/success")
    fun success(): Map<String, Any?> {
        return mapOf(
            "key" to "value"
        )
    }
}