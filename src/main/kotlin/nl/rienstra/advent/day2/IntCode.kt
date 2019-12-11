package nl.rienstra.advent.day2

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class IntCode(private val inputChannel: ReceiveChannel<Long>,
              private val outputChannel: SendChannel<Long>,
              private val label: String? = null) {
    constructor(singleInputValue: Int) : this(listOf(singleInputValue))
    constructor(inputList: List<Int>, createdInputChannel: Channel<Long> = Channel()) : this(createdInputChannel, Channel()) {
        GlobalScope.launch { inputList.forEach { createdInputChannel.send(it.toLong()) } }
    }

    fun run(input: IntArray) =
            runBlocking { runComputer(input.toMap().toMutableMap()) }

    tailrec suspend fun runComputer(input: MutableMap<Long, Long>, index: Long = 0, relativeBase: Long = 0): Map<Long, Long> = if (input[index] != 99L && index < input.size) {
        val output = calculateRow(input, index, relativeBase)
        runComputer(output.input, output.index, output.relativeBase)
    } else {
        println("$label done"); input
    }

    private suspend fun calculateRow(input: MutableMap<Long, Long>, index: Long, relativeBase: Long) = when (input[index].toString().last().toString().toInt()) {
        1 -> ComputerRowOutput(input.apply {
            getParameterMode(input, index, 3, relativeBase)
                    .writeParameterValue(this, index + 3, relativeBase, getParameter(input, index, 1, relativeBase) + getParameter(input, index, 2, relativeBase))
        }, index + 4, relativeBase)
        2 -> ComputerRowOutput(input.apply {
            getParameterMode(input, index, 3, relativeBase)
                    .writeParameterValue(this, index + 3, relativeBase, getParameter(input, index, 1, relativeBase) * getParameter(input, index, 2, relativeBase))
        }, index + 4, relativeBase)
        3 -> {
            ComputerRowOutput(input.apply {
                getParameterMode(input, index, 1, relativeBase)
                        .writeParameterValue(this, index + 1, relativeBase, readInput())
            }, index + 2, relativeBase)
        }
        4 -> {
            println(getParameter(input, index, 1, relativeBase));
            outputChannel.send(getParameter(input, index, 1, relativeBase));
            ComputerRowOutput(input, index + 2, relativeBase)
        }
        5 -> ComputerRowOutput(input, if (getParameter(input, index, 1, relativeBase) != 0L) getParameter(input, index, 2, relativeBase) else index + 3, relativeBase)
        6 -> ComputerRowOutput(input, if (getParameter(input, index, 1, relativeBase) == 0L) getParameter(input, index, 2, relativeBase) else index + 3, relativeBase)
        7 -> ComputerRowOutput(input.apply {
            getParameterMode(input, index, 3, relativeBase)
                    .writeParameterValue(this, index + 3, relativeBase, if (getParameter(input, index, 1, relativeBase) < getParameter(input, index, 2, relativeBase)) 1L else 0L)
        }, index + 4, relativeBase)
        8 -> ComputerRowOutput(input.apply {
            getParameterMode(input, index, 3, relativeBase)
                    .writeParameterValue(this, index + 3, relativeBase, if (getParameter(input, index, 1, relativeBase) == getParameter(input, index, 2, relativeBase)) 1L else 0L)
        }, index + 4, relativeBase)
        9 -> ComputerRowOutput(input, index + 2, relativeBase + getParameter(input, index, 1, relativeBase))
        else -> ComputerRowOutput(input, index + 4, relativeBase)
    }

    private suspend fun readInput(): Long {
        println("$label waiting for input")
        val input = inputChannel.receive()
        println("$label consumed $input")
        return input
    }

    private fun getParameterMode(input: MutableMap<Long, Long>, opIndex: Long, parameterIndex: Int, relativeBase: Long) =
            when {
                input[opIndex] ?: 0 > 99 && parameterIndex == 1 -> getParameterMode(input[opIndex].toString(), parameterIndex)
                input[opIndex] ?: 0 > 999 && parameterIndex == 2 -> getParameterMode(input[opIndex].toString(), parameterIndex)
                input[opIndex] ?: 0 > 9999 && parameterIndex == 3 -> getParameterMode(input[opIndex].toString(), parameterIndex)
                else -> ParameterModes.POSITION
            }

    private fun getParameter(input: MutableMap<Long, Long>, opIndex: Long, parameterIndex: Int, relativeBase: Long) =
            getParameterMode(input, opIndex, parameterIndex, relativeBase).getParameterValue(input, opIndex + parameterIndex, relativeBase)


    private fun getParameterMode(opcode: String, parameterIndex: Int) = when (opcode[opcode.length - (parameterIndex + 2)].toString().toInt()) {
        0 -> ParameterModes.POSITION
        1 -> ParameterModes.IMMEDIATE
        2 -> ParameterModes.RELATIVE
        else -> throw IllegalStateException("Illegal parameter mode")
    }

    private enum class ParameterModes {
        POSITION {
            override fun getParameterValue(input: MutableMap<Long, Long>, parameterIndex: Long, relativeBase: Long) =
                    input[input[parameterIndex]] ?: 0

            override fun writeParameterValue(input: MutableMap<Long, Long>, parameterIndex: Long, relativeBase: Long, value: Long) {
                input[input[parameterIndex] ?: 0] = value
            }
        },
        IMMEDIATE {
            override fun getParameterValue(input: MutableMap<Long, Long>, parameterIndex: Long, relativeBase: Long) =
                    input[parameterIndex] ?: 0

            override fun writeParameterValue(input: MutableMap<Long, Long>, parameterIndex: Long, relativeBase: Long, value: Long) {
                throw IllegalStateException("Write in IMMEDIATE mode not allowed")
            }
        },
        RELATIVE {
            override fun getParameterValue(input: MutableMap<Long, Long>, parameterIndex: Long, relativeBase: Long) =
                    input[relativeBase + (input[parameterIndex] ?: 0)] ?: 0

            override fun writeParameterValue(input: MutableMap<Long, Long>, parameterIndex: Long, relativeBase: Long, value: Long) {
                input[relativeBase + (input[parameterIndex] ?: 0)] = value
            }
        };

        abstract fun getParameterValue(input: MutableMap<Long, Long>, parameterIndex: Long, relativeBase: Long): Long
        abstract fun writeParameterValue(input: MutableMap<Long, Long>, parameterIndex: Long, relativeBase: Long, value: Long): Unit
    }

    data class ComputerRowOutput(val input: MutableMap<Long, Long>, val index: Long, val relativeBase: Long)
}

fun IntArray.toMap() = this.mapIndexed { index, i -> Pair(index, i) }.associateBy({ it.first.toLong() }, { it.second.toLong() })