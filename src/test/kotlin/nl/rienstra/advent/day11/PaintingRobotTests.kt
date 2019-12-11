package nl.rienstra.advent.day11

import io.kotlintest.specs.WordSpec
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import nl.rienstra.advent.day2.IntCode

class PaintingRobotTests : WordSpec({
    "PaintingRobot" should {
        "count panels" {
            val inputValues = this::class.java.getResourceAsStream("/day11/input")
                    .bufferedReader()
                    .readLine()
                    .split(",")
                    .mapIndexed { index, it -> Pair(index.toLong(), it.toLong()) }
                    .toMap()

            val computerInput = Channel<Long>(20)
            val computerOutput = Channel<Long>(20)

            val robot = PaintingRobot(computerOutput, computerInput)
            GlobalScope.async {
                computerInput.send(1)
                robot.paint()
            }

            runBlocking {
                IntCode(computerInput, computerOutput, "robotComputer").runComputer(inputValues.toMutableMap())
                delay(500)
                robot.print()
            }


        }
    }
})