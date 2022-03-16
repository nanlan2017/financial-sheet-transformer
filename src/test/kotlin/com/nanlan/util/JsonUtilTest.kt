package com.nanlan.util

import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class JsonUtilTest {
    @Test
    fun test00() {
        val res = CoreJsonUtil.stringify(
            mapOf(
                "a" to 123
            )
        )
        println(res)
    }

    /*
    应付票据和应付账款周转天数 : m.npaap_ds

    to:

    应付票据和应付账款周转天数("npaap_ds")
     */
    fun is_term_line(line: String): Boolean {
        return listOf("bs.", "ps.", "cfs.", "m.").any { line.contains(it) }
    }

    fun trans2termEntry(line: String): String {
        val splitted = line.split(":")
        return "`${splitted[0].trim()}`(\"${splitted[1].trim().split(".")[1]}\"),"
    }

    @Test
    fun test000() {
        val fis = FileInputStream("src/main/resources/terms.raw.txt")
        val br = BufferedReader(InputStreamReader(fis))
        val lines = mutableListOf<String>()
        while (true) {
            val line = br.readLine() ?: break
            lines.add(line)
        }
        fis.close()

        lines.forEach {
            if (is_term_line(it)) {
                println(trans2termEntry(it))
            } else {
                println(it)
            }
        }

    }
}