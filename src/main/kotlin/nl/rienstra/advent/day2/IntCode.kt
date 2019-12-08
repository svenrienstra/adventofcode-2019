package nl.rienstra.advent.day2

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class IntCode(private val inputChannel: ReceiveChannel<Int>,
              private val outputChannel: SendChannel<Int>,
              private val label: String? = null) {
    constructor(singleInputValue: Int) : this(listOf(singleInputValue))
    constructor(inputList: List<Int>, createdInputChannel: Channel<Int> = Channel()) : this(createdInputChannel, Channel()) {
        GlobalScope.launch { inputList.forEach { createdInputChannel.send(it) } }
    }
    
    fun run(input: IntArray) = runBlocking { runComputer(input) }

    tailrec suspend fun runComputer(input: IntArray, index: Int = 0): IntArray = if (input[index] != 99 && index < input.size) {
        val output = calculateRow(input, index)
        runComputer(output.first, output.second)
    } else {
        println("$label done"); input
    }

    private suspend fun calculateRow(input: IntArray, index: Int) = when (input[index].toString().last().toString().toInt()) {
        1 -> Pair(input.copyOf().apply { this[input[index + 3]] = getParameter(input, index, 1) + getParameter(input, index, 2) }, index + 4)
        2 -> Pair(input.copyOf().apply { this[input[index + 3]] = getParameter(input, index, 1) * getParameter(input, index, 2) }, index + 4)
        3 -> {
            Pair(input.copyOf().apply { this[input[index + 1]] = readInput() }, index + 2)
        }
        4 -> {
            println(getParameter(input, index, 1));
            outputChannel.send(getParameter(input, index, 1));
            Pair(input.copyOf(), index + 2)
        }
        5 -> Pair(input.copyOf(), if (getParameter(input, index, 1) != 0) getParameter(input, index, 2) else index + 3)
        6 -> Pair(input.copyOf(), if (getParameter(input, index, 1) == 0) getParameter(input, index, 2) else index + 3)
        7 -> Pair(input.copyOf().apply { this[input[index + 3]] = if (getParameter(input, index, 1) < getParameter(input, index, 2)) 1 else 0 }, index + 4)
        8 -> Pair(input.copyOf().apply { this[input[index + 3]] = if (getParameter(input, index, 1) == getParameter(input, index, 2)) 1 else 0 }, index + 4)
        else -> Pair(input.copyOf(), index + 4)
    }

    private suspend fun readInput(): Int {
        val input = inputChannel.receive()
        println("$label consumed $input")
        return input
    }

    private fun getParameter(input: IntArray, opIndex: Int, parameterIndex: Int) =
            when {
                input[opIndex] > 99 && parameterIndex == 1 -> getParameterMode(input[opIndex].toString(), parameterIndex).getParameterValue(input, opIndex + parameterIndex)
                input[opIndex] > 999 && parameterIndex == 2 -> getParameterMode(input[opIndex].toString(), parameterIndex).getParameterValue(input, opIndex + parameterIndex)
                //input[opIndex] > 9999 && parameterIndex == 3 -> getParameterMode(input[opIndex].toString(), parameterIndex).getParameterValue(input, opIndex + parameterIndex)
                else -> ParameterModes.POSITION.getParameterValue(input, opIndex + parameterIndex)
            }

    private fun getParameterMode(opcode: String, parameterIndex: Int) = when (opcode[opcode.length - (parameterIndex + 2)].toString().toInt()) {
        0 -> ParameterModes.POSITION
        1 -> ParameterModes.IMMEDIATE
        else -> throw IllegalStateException("Illegal parameter mode")
    }

    private enum class ParameterModes {
        POSITION {
            override fun getParameterValue(input: IntArray, parameterIndex: Int) =
                    input[input[parameterIndex]]
        },
        IMMEDIATE {
            override fun getParameterValue(input: IntArray, parameterIndex: Int) =
                    input[parameterIndex]
        };

        abstract fun getParameterValue(input: IntArray, parameterIndex: Int): Int
    }
}