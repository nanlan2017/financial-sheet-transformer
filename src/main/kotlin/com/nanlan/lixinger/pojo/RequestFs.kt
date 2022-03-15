package com.nanlan.lixinger.pojo

data class RequestFs(
    val date: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val stockCodes: List<String>,
    val metricsList: List<String>
) : AbstractRequest()