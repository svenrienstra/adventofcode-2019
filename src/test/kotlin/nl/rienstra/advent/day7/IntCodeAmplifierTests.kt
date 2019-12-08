package nl.rienstra.advent.day7

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class IntCodeAmplifierTests : WordSpec() {

    init {
        "IntCodeAmplifier" should {
//            "run example" {
//                IntCodeAmplifier(listOf(9,8,7,6,5), intArrayOf(3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
//                        27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5)).calculate() shouldBe 139629729
//                IntCodeAmplifier(listOf(0, 1, 2, 3, 4), intArrayOf(3, 23, 3, 24, 1002, 24, 10, 24, 1002, 23, -1, 23,
//                        101, 5, 23, 23, 1, 24, 23, 23, 4, 23, 99, 0, 0)).calculate() shouldBe 54321
//                IntCodeAmplifier(listOf(1, 0, 4, 3, 2), intArrayOf(3, 31, 3, 32, 1002, 32, 10, 32, 1001, 31, -2, 31, 1007, 31, 0, 33,
//                        1002, 33, 7, 33, 1, 33, 31, 31, 1, 32, 31, 31, 4, 31, 99, 0, 0, 0)).calculate() shouldBe 65210
//            }

//            "calculate highest output" {
//                generate(intArrayOf(3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,
//                        27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5))
//                        .maxBy { it.value }?.key shouldBe listOf(9,8,7,6,5)
//            }

            "find highest output" {
                val input = this::class.java.getResourceAsStream("/day7/input")
                        .bufferedReader()
                        .readLine()
                        .split(",")
                        .map { it.toInt() }
                        .toIntArray()
                val output = generate(input)
                        .maxBy { it.value }
                println(output?.value)
            }

        }
    }

    fun generate(input: IntArray): Map<List<Int>, Int> {
        val inputs = mutableMapOf<List<Int>, Int>()
        for (i in 55555..99999) {
            try {
                val list = i.toString().padStart(5, '0').map { it.toString().toInt() }.toList();
                if (list.toSet().size == 5 && list.all { it >= 5 }) {
                    list.let {
                        inputs[it] = IntCodeAmplifier(it, input).calculate()
                    }
                }
            } catch (exception: Exception) {
                println("$i failed to calculate")
            }

        }

        return inputs
    }
}
