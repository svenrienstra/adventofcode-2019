package nl.rienstra.advent.day9

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import nl.rienstra.advent.day2.IntCode

class RelativeIntComputerTests : WordSpec({
    "IntComputer" should {
//        "calculate examples" {
//            runBlocking {
//                val output = Channel<Long>(20)
//                val input = Channel<Long>(20)
//                IntCode(input, output)
//                        .run(intArrayOf(109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99))
//
//                val result = mutableListOf<Int>()
//                repeat(16) {
//                    result.add(output.receive().toInt())
//                }
//
//                result shouldBe listOf(109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99)
//            }
//
//            runBlocking {
//                val output = Channel<Long>(20)
//                val input = Channel<Long>(20)
//                IntCode(input, output)
//                        .run(intArrayOf(1102, 34915192, 34915192, 7, 4, 7, 99, 0))
//                val result = output.receive()
//                result.toString().length shouldBe 16
//            }
//
//            runBlocking {
//                val output = Channel<Long>(20)
//                val input = Channel<Long>(20)
//                IntCode(input, output).runComputer(mapOf(Pair(0L, 104L), Pair(1L, 1125899906842624L), Pair(2L, 99L)).toMutableMap())
//                val result = output.receive()
//                result shouldBe 1125899906842624L
//            }
//        }

        "calculate output" {
            val inputValues = this::class.java.getResourceAsStream("/day9/input")
                    .bufferedReader()
                    .readLine()
                    .split(",")
                    .mapIndexed { index, it -> Pair(index.toLong(), it.toLong()) }
                    .toMap()

            runBlocking {
                val output = Channel<Long>(20)
                val input = Channel<Long>(20)

                input.send(2L)

                IntCode(input, output, "relative").runComputer(inputValues.toMutableMap())

                val result = output.receive()
                println(result)
            }
        }
    }
})