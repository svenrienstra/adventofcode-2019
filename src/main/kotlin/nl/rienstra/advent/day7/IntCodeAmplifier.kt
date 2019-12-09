package nl.rienstra.advent.day7

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import nl.rienstra.advent.day2.IntCode
import nl.rienstra.advent.day2.toMap
import java.lang.Exception

class IntCodeAmplifier(val inputs: List<Int>, val input: IntArray) {
    fun calculate(): Int {

        val inputChannelA = Channel<Long>(5)
        val inputChannelB = Channel<Long>(5)
        val inputChannelC = Channel<Long>(5)
        val inputChannelD = Channel<Long>(5)
        val inputChannelE = Channel<Long>(5)

        GlobalScope.async {
            inputChannelA.send(inputs[0].toLong())
            inputChannelB.send(inputs[1].toLong())
            inputChannelC.send(inputs[2].toLong())
            inputChannelD.send(inputs[3].toLong())
            inputChannelE.send(inputs[4].toLong())

            inputChannelA.send(0)
        }

        val computerA = IntCode(inputChannelA, inputChannelB, "A")
        val computerB = IntCode(inputChannelB, inputChannelC, "B")
        val computerC = IntCode(inputChannelC, inputChannelD, "C")
        val computerD = IntCode(inputChannelD, inputChannelE, "D")
        val computerE = IntCode(inputChannelE, inputChannelA, "E")

        val a = GlobalScope.async { computerA.runComputer(input.toMap().toMutableMap()) }
        val b = GlobalScope.async { computerB.runComputer(input.toMap().toMutableMap()) }
        val c = GlobalScope.async { computerC.runComputer(input.toMap().toMutableMap()) }
        val d = GlobalScope.async { computerD.runComputer(input.toMap().toMutableMap()) }
        val e = GlobalScope.async { computerE.runComputer(input.toMap().toMutableMap()) }

        return runBlocking {
            e.await()
            inputChannelA.receive().toInt()
        }
    }
}