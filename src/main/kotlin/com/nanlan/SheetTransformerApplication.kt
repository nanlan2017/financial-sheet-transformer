package com.nanlan

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SheetTransformerApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<SheetTransformerApplication>(*args)
        }
    }
}