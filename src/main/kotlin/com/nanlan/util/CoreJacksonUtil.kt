package com.nanlan.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.cfg.MapperConfig
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

object CoreJacksonUtil {

    fun configure(objectMapper: ObjectMapper) = objectMapper
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        //.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        /**
         * WARN: DO NOT enable this feature unless you are fully aware what you are doing,
         * hint: It may expose sensitive business data to public network
         */
        .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
        .registerModule(JavaTimeModule())
        .registerKotlinModule()
        .setPropertyNamingStrategy(FixBooleanPropertyNamingStrategy)!!

    val objectMapper = configure(ObjectMapper())

    val prettyWriter = objectMapper.writerWithDefaultPrettyPrinter()!!

    fun <T : Any> convert(
        source: Any,
        model_class: KClass<T>
    ): T {
        return objectMapper.convertValue(source, model_class.java)
    }

    fun <T> convert(
        source: Any,
        type: TypeReference<T>
    ): T {
        return objectMapper.convertValue(source, type)
    }

    inline fun <reified T : Any> convert(source: Any): T {
        return convert(source, T::class)
    }

    object FixBooleanPropertyNamingStrategy : PropertyNamingStrategy() {

        private val logger = LoggerFactory.getLogger(FixBooleanPropertyNamingStrategy::class.java)

        override fun nameForGetterMethod(
            config: MapperConfig<*>?,
            method: AnnotatedMethod,
            defaultName: String?
        ): String {
            if (method.hasReturnType()
                && (method.rawReturnType == Boolean::class.javaObjectType || method.rawReturnType == Boolean::class.javaPrimitiveType)
                && method.name.startsWith("is")
            ) {
                return method.name
            }
            return super.nameForGetterMethod(config, method, defaultName)
        }

        /**
         * if
         * 1. method name starts with set, e.g. setFoo
         * 2. return type is Boolean or boolean(jvm primitive type)
         * 3. the declaring class contains a method named isFoo
         * we return isFoo as the property name in json
         *
         * refer to https://stackoverflow.com/a/58999529
         */
        override fun nameForSetterMethod(
            config: MapperConfig<*>?,
            method: AnnotatedMethod,
            defaultName: String?
        ): String {
            if (method.parameterCount == 1
                && (method.getRawParameterType(0) == Boolean::class.javaObjectType || method.getRawParameterType(0) == Boolean::class.javaPrimitiveType)
                && method.name.startsWith("set")
            ) {
                try {
                    val potential_name = "is${method.name.substring(3)}"
                    method.declaringClass.getMethod(potential_name)
                    return potential_name
                } catch (e: NoSuchMethodException) {
                }
            }
            return super.nameForSetterMethod(config, method, defaultName)
        }
    }

}
