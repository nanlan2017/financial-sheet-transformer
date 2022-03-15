package com.nanlan.lixinger.pojo

class PieceData {
    companion object {
        fun flatten(mapOfMap: Map<String, Map<String, Any>>): Map<String, Any> {
            val res = mutableMapOf<String, Any>()
            val outerKeys = mapOfMap.keys
            outerKeys.forEach { outerKey ->
                val map = mapOfMap[outerKey]!!
                map.entries.forEach {
                    res["$outerKey.${it.key}"] = it.value
                }
            }
            return res
        }
    }

    var stockCode: String = ""
    var currency: String = ""
    var standardDate: String = ""
    var reportDate: String = ""
    var reportType: String = ""
    var date: String = ""


    var y: Map<String, Map<String, Map<String, Any>>>? = null
    var q: Map<String, Map<String, Map<String, Any>>>? = null
    var hy: Map<String, Map<String, Map<String, Any>>>? = null


    /*
      "y": {
        "ps": {
          "toi": {
            "t": 50319487697
          },
          "op": {
            "t": 6959489551
          }
        }
      }
     */
    val metrics: Map<String, Any> by lazy {
        val map = mutableMapOf<String, Map<String, Any>>()
        y?.mapValues { flatten(it.value) }?.let { flatten(it) }?.let {
            map["y"] = it
        }
        q?.mapValues { flatten(it.value) }?.let { flatten(it) }?.let {
            map["q"] = it
        }
        hy?.mapValues { flatten(it.value) }?.let { flatten(it) }?.let {
            map["hy"] = it
        }
        flatten(map).toSortedMap()
    }
}