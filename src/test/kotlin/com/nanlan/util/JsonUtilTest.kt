package com.nanlan.util

import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream

class JsonUtilTest {
    @Test
    fun test00() {
        val res = CoreJsonUtil.stringify(mapOf(
            "a" to 123
        ))
        println(res)
    }

    /*
    应付票据和应付账款周转天数 : m.npaap_ds

    to:

    应付票据和应付账款周转天数("npaap_ds")
     */
    @Test
    fun test000() {
        val fis = FileInputStream(File("terms.txt"))
    }
}