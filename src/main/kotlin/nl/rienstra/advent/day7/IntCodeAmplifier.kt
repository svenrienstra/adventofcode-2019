package nl.rienstra.advent.day7

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.channels.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import nl.rienstra.advent.day2.IntCode
import java.lang.Exception

class IntCodeAmplifier(val inputs: List<Int>, val input: IntArray) {
    fun calculate(): Int {

        val inputChannelA = Channel<Int>(5)
        val inputChannelB = Channel<Int>(5)
        val inputChannelC = Channel<Int>(5)
        val inputChannelD = Channel<Int>(5)
        val inputChannelE = Channel<Int>(5)

        GlobalScope.async {
            inputChannelA.send(inputs[0])
            inputChannelB.send(inputs[1])
            inputChannelC.send(inputs[2])
            inputChannelD.send(inputs[3])
            inputChannelE.send(inputs[4])

            inputChannelA.send(0)
        }

        val computerA = IntCode(inputChannelA, inputChannelB, "A")
        val computerB = IntCode(inputChannelB, inputChannelC, "B")
        val computerC = IntCode(inputChannelC, inputChannelD, "C")
        val computerD = IntCode(inputChannelD, inputChannelE, "D")
        val computerE = IntCode(inputChannelE, inputChannelA, "E")

        val a = GlobalScope.async { computerA.runComputer(input) }
        val b = GlobalScope.async { computerB.runComputer(input) }
        val c = GlobalScope.async { computerC.runComputer(input) }
        val d = GlobalScope.async { computerD.runComputer(input) }
        val e = GlobalScope.async { computerE.runComputer(input) }

        return runBlocking {
            e.await()
            inputChannelA.receive()
        }
    }
}