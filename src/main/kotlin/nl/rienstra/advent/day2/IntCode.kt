package nl.rienstra.advent.day2

object IntCode {
    fun run(input: IntArray, index: Int = 0): IntArray = if (input[index] != 99 && index < input.size) run(calculateRow(input, index), index + 4)  else input

    private fun calculateRow(input: IntArray, index: Int) = when (input[index]) {
        1 -> input.copyOf().apply { this[input[index + 3]] = input[input[index + 1]] + input[input[index + 2]] }
        2 -> input.copyOf().apply { this[input[index + 3]] = input[input[index + 1]] * input[input[index + 2]] }
        else -> input.copyOf()
    }
}