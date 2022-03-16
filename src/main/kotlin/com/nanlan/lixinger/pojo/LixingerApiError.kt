package com.nanlan.lixinger.pojo

data class LixingerApiError(
    val messages: List<Map<String, Any?>> = emptyList(),
    val name: String
)
