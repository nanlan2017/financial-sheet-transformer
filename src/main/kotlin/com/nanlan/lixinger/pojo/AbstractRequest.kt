package com.nanlan.lixinger.pojo

import com.nanlan.util.RequestUtil

abstract class AbstractRequest {
    var token: String = "35a572a3-bd7b-4e35-affa-7338f1cb9489"

    fun toJsonString(): String {
        return RequestUtil.stringify(this)
    }
}