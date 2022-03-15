package com.nanlan.util

class DateBuilder {
    var year: Int? = null
    var month: Int? = null
    var day: Int? = null

    fun year(year: Int): DateBuilder {
        return this.apply {
            this.year = year
        }
    }
}