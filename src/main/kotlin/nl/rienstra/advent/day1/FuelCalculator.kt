package nl.rienstra.advent.day1

import kotlin.math.floor

object FuelCalculator {
    fun calculate(input: Int) = (floor((input.toDouble() / 3)) - 2).toInt()
    fun calculateWithAdditionalFuel(input: Int) = calculateAdditionalFuel(calculate(input))

    private fun calculateAdditionalFuel(input: Int): Int = if (calculate(input) > 0) input + calculateAdditionalFuel(calculate(input)) else input
}