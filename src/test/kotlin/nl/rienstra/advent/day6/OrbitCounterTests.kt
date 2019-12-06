package nl.rienstra.advent.day6

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class OrbitCounterTests : WordSpec({

    "OrbitCounter" should {
        "count example orbits" {
            OrbitCounter.countOrbits(listOf("COM)B",
                    "B)C",
                    "C)D",
                    "D)E",
                    "E)F",
                    "B)G",
                    "G)H",
                    "D)I",
                    "E)J",
                    "J)K",
                    "K)L"), "D") shouldBe 3

            OrbitCounter.countOrbits(listOf("COM)B",
                    "B)C",
                    "C)D",
                    "D)E",
                    "E)F",
                    "B)G",
                    "G)H",
                    "D)I",
                    "E)J",
                    "J)K",
                    "K)L"), "L") shouldBe 7

            OrbitCounter.countAllOrbits(listOf(
                    "B)C",
                    "C)D",
                    "D)E",
                    "E)F",
                    "B)G",
                    "COM)B",
                    "G)H",
                    "D)I",
                    "E)J",
                    "J)K",
                    "K)L")) shouldBe 42
        }

        "calculate answer" {
            val input = this::class.java.getResourceAsStream("/day6/input")
                    .bufferedReader()
                    .readLines()

            println(OrbitCounter.countAllOrbits(input))
        }

        "calculate distance example" {
            val input = this::class.java.getResourceAsStream("/day6/distance_example")
                    .bufferedReader()
                    .readLines()

            OrbitCounter.calculateNumberOfTransfers(input, "YOU", "SAN") shouldBe 4
        }

        "calculate distance" {
            val input = this::class.java.getResourceAsStream("/day6/input")
                    .bufferedReader()
                    .readLines()

            println(OrbitCounter.calculateNumberOfTransfers(input, "YOU", "SAN"))
        }
    }
})