fun main() {
    val dividend = 10
    val divisor = 3

    val (a, b) = divide(dividend, divisor)
}

data class DivisionResult(val quotient: Int, val remainder: Int)

fun divide (dividend: Int, divisor: Int) : DivisionResult {
    val quotient = dividend.div(divisor)
    val remainder = dividend.rem(divisor)
    return DivisionResult(quotient, remainder)
}
