package nl.rienstra.advent.day3

import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class WireLocatorTests : WordSpec({

    "WireLocator" should {
        "find intersections" {
            WireLocator.calculateIntersections(listOf("R8", "U5", "L5", "D3"), listOf("U7", "R6", "D4", "L4")) shouldContainAll setOf(WireLocator.Point(3, 3), WireLocator.Point(5, 6))
        }

        "calculateDistance" {
            WireLocator.calculateLowestDistance(listOf("R8", "U5", "L5", "D3"), listOf("U7", "R6", "D4", "L4")) shouldBe 6
            WireLocator.calculateLowestDistance(
                    listOf("R75", "D30", "R83", "U83", "L12", "D49", "R71", "U7", "L72"),
                    listOf("U62", "R66", "U55", "R34", "D71", "R55", "D58", "R83")) shouldBe 159
            WireLocator.calculateLowestDistance(
                    listOf("R98", "U47", "R26", "D63", "R33", "U87", "L62", "D20", "R33", "U53", "R5"),
                    listOf("U98", "R91", "D20", "R16", "D67", "R40", "U7", "R15", "U6", "R7")) shouldBe 135
        }

        "calculate answer" {
            var inputs = this::class.java.getResourceAsStream("/day3/input")
                    .bufferedReader()
                    .readLines()

            println(WireLocator.calculateLowestDistance(inputs[0].split(","), inputs[1].split(",")))
        }

        "calculate shortest path" {
            WireLocator.calculateShortestPath(listOf("R8", "U5", "L5", "D3"), listOf("U7", "R6", "D4", "L4")) shouldBe 30

            WireLocator.calculateShortestPath(
                    listOf("R75", "D30", "R83", "U83", "L12", "D49", "R71", "U7", "L72"),
                    listOf("U62", "R66", "U55", "R34", "D71", "R55", "D58", "R83")) shouldBe 610
            WireLocator.calculateShortestPath(
                    listOf("R98", "U47", "R26", "D63", "R33", "U87", "L62", "D20", "R33", "U53", "R5"),
                    listOf("U98", "R91", "D20", "R16", "D67", "R40", "U7", "R15", "U6", "R7")) shouldBe 410
        }

        "calculate shortes path answer" {
            var inputs = this::class.java.getResourceAsStream("/day3/input")
                    .bufferedReader()
                    .readLines()

            println(WireLocator.calculateShortestPath(inputs[0].split(","), inputs[1].split(",")))
        }
    }

})