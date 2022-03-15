package com.nanlan.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object RequestUtil {
    fun configure(objectMapper: ObjectMapper) = objectMapper
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        /**
         * WARN: DO NOT enable this feature unless you are fully aware what you are doing,
         * hint: It may expose sensitive business data to public network
         */
        .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
        .registerModule(JavaTimeModule())
        .registerKotlinModule()
        .setPropertyNamingStrategy(CoreJacksonUtil.FixBooleanPropertyNamingStrategy)!!

    val objectMapper = configure(ObjectMapper())

    fun stringify(obj: Any?): String {
        return objectMapper.writeValueAsString(obj)
    }
}