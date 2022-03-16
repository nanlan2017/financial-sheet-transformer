package com.nanlan.util.extensions

import com.nanlan.util.Formatters
import java.math.BigDecimal
import java.math.RoundingMode


val Int.BD: BigDecimal
    get() = this.toBigDecimal()
val Long.BD: BigDecimal
    get() = this.toBigDecimal()
val Float.BD: BigDecimal
    get() = this.toBigDecimal()
val Double.BD: BigDecimal
    get() = this.toBigDecimal()
val String.BD: BigDecimal
    get() = this.toBigDecimal()

/**
 * 1.000 -> 1.00
 * 1.001 -> 1.001
 */
fun BigDecimal.safe_change_precision_to_2digits(): BigDecimal {
    return Formatters.NUMBER.`dot00#`.format(this).toBigDecimal()
}

/**
 *  0.273 -> 27%
 */
fun BigDecimal.percent(
    other: BigDecimal,
    digits: Int = 2,
    if_zero_value: String = "",
): String {
    return if (other == 0.BD) {
        if_zero_value
    } else {
        val t = this.divide(other, digits, RoundingMode.HALF_UP)
        t.multiply(100.BD).toInt().let { "$it%" }
    }
}

fun BigDecimal.to_percent_str(): String {
    return this.multiply(100.BD).toInt().let { "$it%" }
}

infix fun BigDecimal?.eq(another: BigDecimal?): Boolean {
    if (this == null && another == null) return true
    if (this != null && another != null) return this.compareTo(another) == 0
    return false
}

infix fun BigDecimal?.eq(another: Int?) = this.eq(another?.BD)
infix fun BigDecimal?.eq(another: Double?) = this.eq(another?.BD)

infix fun BigDecimal?.ne(another: BigDecimal?) = !this.eq(another)
infix fun BigDecimal?.ne(another: Int?) = !this.eq(another)
infix fun BigDecimal?.ne(another: Double?) = !this.eq(another)

val Iterable<BigDecimal>.sum: BigDecimal
    get() = this.fold(0.BD, BigDecimal::add)

inline fun <T> Iterable<T>.sumBdBy(selector: (T) -> BigDecimal) = this.map(selector).sum

