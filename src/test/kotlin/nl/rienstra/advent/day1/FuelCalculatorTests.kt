package nl.rienstra.advent.day1

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class FuelCalculatorTests : WordSpec({
    "FuelCalculator" should {
        "calculate samples" {
            FuelCalculator.calculate(12) shouldBe 2
            FuelCalculator.calculate(14) shouldBe 2
            FuelCalculator.calculate(1969) shouldBe 654
            FuelCalculator.calculate(100756) shouldBe 33583
        }

        "calculate additional fuel requirements" {
            FuelCalculator.calculateWithAdditionalFuel(1969) shouldBe 966
            FuelCalculator.calculateWithAdditionalFuel(100756) shouldBe 50346
        }

        "calculate fuel requirements" {
            var result = this::class.java.getResourceAsStream("/day1/input")
                    .bufferedReader()
                    .readLines()
                    .stream()
                    .mapToInt { line -> FuelCalculator.calculateWithAdditionalFuel(line.toInt()) }
                    .sum()

            println(result)

        }
    }
})