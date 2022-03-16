package com.nanlan.lixinger


/**
 *  累积:t,  当期:c, TTM:ttm
 *  同比:_y2y, 环比:_c2c, 占比:_2y
 */
// @Deprecated("Just remember these entries")
enum class CalcType(val key: String) {
    累积("t"),
    累积同比("t_y2y"),
    累积环比("t_c2c"),

    当期("c"),
    当期同比("c_y2y"),
    当期环比("c_c2c"),
    当前占比("c_2y"),

    TTM("ttm"),
    TTM同比("ttm_y2y"),
    TTM环比("ttm_c2c")
}