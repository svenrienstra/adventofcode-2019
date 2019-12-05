package nl.rienstra.advent.day5

import io.kotlintest.specs.WordSpec
import nl.rienstra.advent.day2.IntCode

class IntcodeTests : WordSpec({

    "IntCode" should {
        "test 5b" {
            IntCode(8).run(intArrayOf(3,9,8,9,10,9,4,9,99,-1,8))
            IntCode(9).run(intArrayOf(3,9,8,9,10,9,4,9,99,-1,8))

            IntCode(7).run(intArrayOf(3,9,7,9,10,9,4,9,99,-1,8))
            IntCode(8).run(intArrayOf(3,9,7,9,10,9,4,9,99,-1,8))

            IntCode(8).run(intArrayOf(3,3,1108,-1,8,3,4,3,99))
            IntCode(9).run(intArrayOf(3,3,1108,-1,8,3,4,3,99))

            IntCode(7).run(intArrayOf(3,3,1107,-1,8,3,4,3,99))
            IntCode(8).run(intArrayOf(3,3,1107,-1,8,3,4,3,99))

            IntCode(0).run(intArrayOf(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9))
            IntCode(8).run(intArrayOf(3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9))

            IntCode(0).run(intArrayOf(3,3,1105,-1,9,1101,0,0,12,4,12,99,1))
            IntCode(8).run(intArrayOf(3,3,1105,-1,9,1101,0,0,12,4,12,99,1))

            IntCode(0).run(intArrayOf(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
                    1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
                    999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99))
            IntCode(8).run(intArrayOf(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
                    1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
                    999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99))
            IntCode(10).run(intArrayOf(3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
                    1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
                    999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99))
        }

        "execute" {
            val input = this::class.java.getResourceAsStream("/day5/input")
                    .bufferedReader()
                    .readLine()
                    .split(",")
                    .map { it.toInt() }
                    .toIntArray()

            println(IntCode(5).run(input).asList())
        }
    }

})