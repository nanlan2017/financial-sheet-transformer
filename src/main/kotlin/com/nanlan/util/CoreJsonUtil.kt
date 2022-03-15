package com.nanlan.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.fasterxml.jackson.module.kotlin.readValue
import com.nanlan.util.CoreJacksonUtil.objectMapper
import com.nanlan.util.CoreJacksonUtil.prettyWriter
import kotlin.reflect.KClass

object CoreJsonUtil {

    fun stringify(obj: Any?, pretty: Boolean = false): String {
        return if (pretty)
            prettyWriter.writeValueAsString(obj)
        else
            objectMapper.writeValueAsString(obj)
    }

    fun <T : Any> parse(json: String, clazz: KClass<T>): T {
        return objectMapper.readValue(json, clazz.java)
    }

    fun <T : Any> parse(json: String, type: TypeReference<T>): T {
        return objectMapper.readValue(json, type)
    }

    inline fun <reified T : Any> parse(json: String): T {
        return objectMapper.readValue(json, jacksonTypeRef<T>())
    }

    private val regex = """\??\.?([a-zA-Z0-9_]+)|\??\[(\d+)]""".toRegex()

    /**
     * use dot, bracket to access json property
     *
     * e.g. `getProperty<Qux>(json, "foo?.bar[1]?.baz.qux")`
     * see com.cpvsn.core.util.CoreJsonUtilGetPropertyTest
     */
//    inline fun <reified T> getProperty(
//        json: String,
//        path: String,
//    ): T {
//        val res = getProperty(json, path, jacksonTypeRef<T>())
//        if (res == null && null !is T) {
//            throw NullPointerException("expect a non-null value, whereas the result is null")
//        }
//        return res
//    }

    fun <T : Any?> getProperty(
        json: String,
        path: String,
        expectType: TypeReference<T>,
    ): T {
        var node: Any? = objectMapper.readValue(json)
        val res = regex.findAll(path)
        var chain = ""
        res.forEach {
            chain += it.value
            val optional = it.value.startsWith("?")
            val propName = if (it.groupValues[1].isBlank()) {
                it.groupValues[2].toInt()
            } else {
                it.groupValues[1]
            }
            require(propName is String || propName is Int) {
                "path arguments can only be String or Int"
            }
            when (node) {
                is Map<*, *> -> {
                    if (propName is String) {
                        node = (node as Map<*, *>)[propName]
                    }
                    if (propName is Int) {
                        error(propName, node, chain)
                    }
                }
                is List<*> -> {
                    if (propName is String) {
                        error(propName, node, chain)
                    }
                    if (propName is Int) {
                        node = (node as List<*>).getOrNull(propName)
                    }
                }
                is String, is Number, is Boolean -> {
                    error(propName, node, chain)
                }
                else -> {
                    // node is null
                    if (optional) {
                        @Suppress("UNCHECKED_CAST")
                        return null as T
                    } else {
                        error(propName, node, chain)
                    }
                }
            }
        }
        @Suppress("UNCHECKED_CAST")
        return node?.let {
            CoreJacksonUtil.convert(it, expectType)
        } as T
    }

    private fun error(
        propName: Any,
        node: Any?,
        chain: String,
    ) {
        error("Cannot use '$propName' to access '$node', access chain = $chain")
    }
}
