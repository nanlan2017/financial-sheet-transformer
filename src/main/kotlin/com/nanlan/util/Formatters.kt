package com.nanlan.util

import java.text.DecimalFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Formatters {
    @Suppress("ObjectPropertyName")
    object DATETIME {
        val `yyyyMMdd HHmmss` = DateTimeFormatter
            .ofPattern("yyyyMMdd HH:mm:ss")
            .withZone(ZoneId.systemDefault())!!
        val `yyyy-MM-dd HHmmss`: DateTimeFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault())!!
        val yyyyMMdd = DateTimeFormatter
            .ofPattern("yyyyMMdd")
            .withZone(ZoneId.systemDefault())!!
        val `yyyy-MM-dd` = DateTimeFormatter
            .ofPattern("yyyy-MM-dd")
            .withZone(ZoneId.systemDefault())!!
        val HHmm = DateTimeFormatter
            .ofPattern("HH:mm")
            .withZone(ZoneId.systemDefault())!!
        val HHmmss = DateTimeFormatter
            .ofPattern("HH:mm:ss")
            .withZone(ZoneId.systemDefault())!!
    }

    @Suppress("ObjectPropertyName")
    object NUMBER {
        val dot0 = DecimalFormat("#.0")
        val dot00 = DecimalFormat("#.00")
        val `dot00#` = DecimalFormat("#.00#")
    }
}

fun DateTimeFormatter.format_with_zone_id(instant: Instant, zoneId: ZoneId): String {
    val date = this.withZone(zoneId).format(instant)
    return "$date ($zoneId)"
}

