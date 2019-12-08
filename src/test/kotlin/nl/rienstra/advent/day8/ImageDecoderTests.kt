package nl.rienstra.advent.day8

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

class ImageDecoderTests : WordSpec({
    "ImageDecoder" should {
        "decode layers" {
            val layers = ImageDecoder.decode(3, 2, intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2))
            layers[0].rows[0][0] shouldBe 1
            layers[0].rows[1][1] shouldBe 5
            layers[1].rows[1][2] shouldBe 2
        }

        "find answer" {
            val input = this::class.java.getResourceAsStream("/day8/input")
                    .bufferedReader()
                    .readLine()
                    .map { it.toString().toInt() }
                    .toIntArray()

            val layers = ImageDecoder.decode(25, 6, input)
            val result = layers.map {
                Pair(it.countNumber(0), it.countNumber(1) * it.countNumber(2))
            }.minBy { it.first }

            println(result!!.second)
        }

        "print image" {
            val input = this::class.java.getResourceAsStream("/day8/input")
                    .bufferedReader()
                    .readLine()
                    .map { it.toString().toInt() }
                    .toIntArray()

            ImageDecoder.print(25, 6, input)
        }
    }
})