package com.nanlan.lixinger

import com.nanlan.lixinger.pojo.AbstractRequest
import com.nanlan.lixinger.pojo.AbstractResponse
import com.nanlan.util.CoreJsonUtil
import kong.unirest.Unirest
import kotlin.reflect.KClass

object Lixinger {
    fun <R : AbstractResponse> post(url: String, request: AbstractRequest, responseClass: KClass<R>): R {
        val httpResponse = Unirest.post(url)
            .header("Content-Type", "application/json")
            .body(request.toJsonString())
            .asString()
        val response = CoreJsonUtil.parse(httpResponse.body, responseClass)
        return response
    }
}