package com.nanlan.lixinger.pojo


abstract class AbstractResponse {
    var code: String = ""
    var message: String = ""
    var error: LixingerApiError? = null
}